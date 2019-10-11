package com.concurrency.service.impl;

import com.concurrency.entity.pojo.Product;
import com.concurrency.entity.pojo.PurchaseRecord;
import com.concurrency.mapper.ProductMapper;
import com.concurrency.mapper.PurchaseRecordMapper;
import com.concurrency.service.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.Jedis;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

/**
 * @program: concurrency
 * @description:
 * @author: Ailuoli
 * @create: 2019-06-23 18:26
 **/

@Service
public class PurchaseServiceImpl implements PurchaseService {

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private PurchaseRecordMapper purchaseRecordMapper;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;


    // 产品列表key
    private static final String PRODUCT_SCHEDULE_SET = "product_schedule_set";


    //购买列表key
    private static final String PURCHASE_PRODUCT_LIST = "purchase_list_";


    String purchaseScript =
            //先将产品编号保存到集合中
            "redis.call('sadd',KEYS[1],ARGV[2]) \n"
                    //购买列表
            +"local productPurchaseList = KEYS[2]..ARGV[2] \n"
                    //用户编号，第一个额外参数
            +"local userId = ARGV[1] \n"
                    //产品键，第二个额外参数
            +"local product = 'product_'..ARGV[2] \n"
                    //购买数量，第三个额外参数
            +"local quantity = tonumber(ARGV[3]) \n"
                    //当前库存
            +"local stock = tonumber(redis.call('hget','product','stock')) \n"
                    //价格
            +"local price = tonumber(redis.call('hget','product','price')) \n"
                    //购买时间,第四个额外参数
            +"local purchase_date=ARGV[4] \n"
                    //库存不足，返回0
            +"if stock < quantity then return 0 end \n"
                    //减库存
            +"stock = stock-quantity \n"
            +"redis.call('hset','product','stock',tostring(stock)) \n"
                    //计算价格
            +"local sum = price * quantity \n"
                    //合并购买记录
            +"local purchaseRecord = userId..','..quantity..','"
            +"..sum..','..price..','..purchase_date \n"
                    //将购买记录保存到list里
            +"redis.call('rpush',productPurchaseList,purchaseRecord) \n"
            +"return 1 \n";




    private String sha1 = null;

    @Override
    @Transactional
    public boolean purchase(PurchaseRecord purchaseRecord) {

        //获取当前时间
        Long start = System.currentTimeMillis();

        //循环尝试直至成功

        //也可以限制重入次数来使用乐观锁。
//        for (int i = 0;i<3;i++){
//
//        }
//


        //noinspection LoopStatementThatDoesntLoop
        while (true){

            Long end = System.currentTimeMillis();

            //如果循环时间大于100ms返回终止循环。
            if(end - start > 100){
                return false;
            }
            Product product = productMapper.selectByPrimaryKey(purchaseRecord.getProductId());

            if(product.getStock()<purchaseRecord.getQuantity()){
                return false;
            }

            //获取当前版本号
            int version = product.getVersion();

            int result = productMapper.decreaseProduct(purchaseRecord.getProductId(),purchaseRecord.getQuantity()/*,version*/);
//
//        //扣减库存
//        productMapper.decreaseProduct(purchaseRecord.getProductId(),purchaseRecord.getQuantity());

            //使用乐观锁，一旦版本号发生改变则无法进行跟新数据，注意：版本号只增不减
            //如果数据更新失败，说明数据在多线程中被其他线程修改导致失败，通过循环重入尝试购买商品，
            if(result == 0){
                return false;
            }

            //设置备注
            purchaseRecord.setNote("购买日志，时间："+System.currentTimeMillis());

            //设置单价
            purchaseRecord.setPrice(product.getPrice());

            //设置总价
            purchaseRecord.setSum(product.getPrice().multiply(BigDecimal.valueOf(purchaseRecord.getQuantity())));


            purchaseRecordMapper.concurrencyInsert(purchaseRecord);

            return true;
        }

    }

    @Override
    public boolean purchaseRedis(Long userId, Long productId, int quantity) {

        //购买时间
        long purchaseDate = System.currentTimeMillis();
        Jedis jedis = null;

        try {

            //获取原始连接
            jedis = (Jedis) Objects.requireNonNull(stringRedisTemplate.getConnectionFactory()).getConnection().getNativeConnection();

            //如果没有加载过，则先将脚本加载到Redis服务器，让其返回sha1
            if(sha1 == null){
                sha1 = jedis.scriptLoad(purchaseScript);
            }

            //执行脚本，返回结果
            Object res = jedis.evalsha(sha1,2,PRODUCT_SCHEDULE_SET,PURCHASE_PRODUCT_LIST,userId+"",productId+"",quantity+"",purchaseDate+"");

            Long result = (Long) res;

            return result == 1;

        }finally {
            //关闭连接
            if (jedis!=null && jedis.isConnected()){
                jedis.close();
            }
        }


    }

    /**
     * 传播行为设置为Propagation.REQUIRES_NEW,调用该方法时会挂起，开启新的事务，这样在回滚时只会回滚这个方法内部的事务，而不会影响全局事务。
     * @param purchaseRecordList
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public boolean dealRedisPurchase(List<PurchaseRecord> purchaseRecordList) {

        for(PurchaseRecord temp: purchaseRecordList){
            purchaseRecordMapper.concurrencyInsert(temp);
            productMapper.decreaseProduct(temp.getProductId(),temp.getQuantity());
        }
        return true;
    }


}

