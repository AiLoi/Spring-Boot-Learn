package com.redis.service.impl;

import com.redis.mapper.RedisUserMapper;
import com.redis.pojo.RedisUser;
import com.redis.service.RedisUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @program: redis
 * @description:
 * @author: Ailuoli
 * @create: 2019-06-13 10:30
 **/

@Service
public class RedisUserServiceImpl implements RedisUserService {

    @Autowired
    private RedisUserMapper redisUserMapper;


    /*
    @Cacheable 表示先从缓存中通过定义的键查询，如果可以查询到数据，则返回，否则执行该方法，返回数据，并将返回结果放到缓存中
     */
    @Override
    @Transactional
    @Cacheable(value = "redisCache", key = "'redis_user_'+#id")
    public RedisUser getRedisUser(Integer id) {
        return redisUserMapper.selectByPrimaryKey(id);
    }

    /*
    @CachePut 表示将方法结果存放到缓存中。
     */
    @Override
    @Transactional
    @CachePut(value = "redisCache", key = "'redis_user_'+#result.id")
    public RedisUser insertRedisUser(RedisUser redisUser) {
        redisUserMapper.insertSelective(redisUser);
        return redisUser;
    }

    @Override
    @Transactional
    @CachePut(value = "redisCache", condition = "#result!='null'", key = "'redis_user_'+#id")
    public RedisUser updateUsername(Integer id, String username) {
        //注意这里会直接调用方法，不会使用注解，也就是说这里不会获取redis缓存内的数据
        RedisUser redisUser = this.getRedisUser(id);
        if (redisUser == null) {
            return null;
        }
        redisUser.setUsername(username);
        redisUserMapper.updateByPrimaryKeySelective(redisUser);
        return redisUser;
    }


    /*
    CacheEvict 通过定义的键移除缓存，他有一个Boolean类型的配置项beforeInvocation，表示在方法之前或者之后移除缓存。默认是false，即方法之后将缓存移除
     */
    @Override
    @Transactional
    @CacheEvict(value = "redisCache", key = "'redis_user_'+#id", beforeInvocation = false)
    public int deleteUser(Integer id) {
        return redisUserMapper.deleteByPrimaryKey(id);
    }
}

