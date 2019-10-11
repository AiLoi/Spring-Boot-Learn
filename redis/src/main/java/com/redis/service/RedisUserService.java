package com.redis.service;

import com.redis.pojo.RedisUser;

/**
 * @program: redis
 * @description:
 * @author: Ailuoli
 * @create: 2019-06-13 10:17
 **/
public interface RedisUserService {

    RedisUser getRedisUser(Integer id);

    RedisUser insertRedisUser(RedisUser redisUser);

    RedisUser updateUsername(Integer id, String username);

    int deleteUser(Integer id);

}

