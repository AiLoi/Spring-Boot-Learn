package com.concurrency.service.impl;

import com.concurrency.entity.pojo.PurchaseRecord;
import com.concurrency.service.PurchaseService;
import com.concurrency.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundListOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @program: concurrency
 * @description:
 * @author: Ailuoli
 * @create: 2019-06-24 18:04
 **/

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private PurchaseService purchaseService;

    private static final String PRODUCT_SCHEDULE_SET = "product_schedule_set";

    private static final String PURCHASE_PRODUCT_LIST = "purchase_list_";

    private static final int ONE_TIME_SIZE = 1000;

    @Override
    //每天凌晨1点开始执行任务
    @Scheduled(cron = "0 0 1 * * ?")
    //下面用于测试配置，每分钟执行一次
    //@Scheduled(fixedRate = 1000*60)
    public void purchaseTask() {

        System.out.println("定时任务开启。。。。。。");

        Set<String> productIdList = stringRedisTemplate.opsForSet().members(PRODUCT_SCHEDULE_SET);

        List<PurchaseRecord> purchaseRecordList = new ArrayList<>();

        for (String temp : productIdList){

            int productId = Integer.parseInt(temp);

            String purchaseKey = PURCHASE_PRODUCT_LIST+productId;

            BoundListOperations<String,String> ops = stringRedisTemplate.boundListOps(purchaseKey);

            Long size = stringRedisTemplate.opsForList().size(purchaseKey);

            long times = size % ONE_TIME_SIZE ==0 ? size / ONE_TIME_SIZE:size / ONE_TIME_SIZE +1;

            for (int i = 0 ;i<times;i++){

                List<String> prList = null;
                if (i == 0){
                    prList = ops.range(i * ONE_TIME_SIZE,(i+1) * ONE_TIME_SIZE);
                }else {
                    prList = ops.range(i * ONE_TIME_SIZE + 1,(i+1) * ONE_TIME_SIZE);
                }
                for (String prStr:prList){
                    PurchaseRecord purchaseRecord = this.createPurchaseRecord(productId,prStr);
                    purchaseRecordList.add(purchaseRecord);
                }

                try {
                    purchaseService.dealRedisPurchase(purchaseRecordList);
                }catch (Exception e){
                    e.printStackTrace();
                }

                purchaseRecordList.clear();
            }

            stringRedisTemplate.delete(purchaseKey);
            stringRedisTemplate.opsForSet().remove(PRODUCT_SCHEDULE_SET,temp);

        }
        System.out.println("定时任务结束。。。。。。 ");

    }



    private PurchaseRecord createPurchaseRecord(int productId,String prStr){

        String [] arr = prStr.split(",");

        int userId = Integer.parseInt(arr[0]);

        int quantity = Integer.parseInt(arr[1]);

        double sum = Double.valueOf(arr[2]);

        double price = Double.valueOf(arr[3]);

        long time = Long.parseLong(arr[4]);


        Timestamp timestamp = new Timestamp(time);

        PurchaseRecord purchaseRecord = new PurchaseRecord();

        purchaseRecord.setProductId(productId);

        purchaseRecord.setPurchaseDate(timestamp);

        purchaseRecord.setPrice(BigDecimal.valueOf(price));

        purchaseRecord.setQuantity(quantity);

        purchaseRecord.setSum(BigDecimal.valueOf(sum));

        purchaseRecord.setUserId(userId);

        purchaseRecord.setNote("购买日志，时间："+timestamp.getTime());

        return purchaseRecord;



    }
}

