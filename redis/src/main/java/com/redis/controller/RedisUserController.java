package com.redis.controller;

import com.redis.pojo.RedisUser;
import com.redis.service.RedisUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @program: redis
 * @description:
 * @author: Ailuoli
 * @create: 2019-06-13 11:19
 **/
@RestController
@RequestMapping("/redis/user")
public class RedisUserController {


    @Autowired
    private RedisUserService redisUserService;


    @PostMapping("/get_redis_user")
    public RedisUser getRedisUser(Integer id) {
        return redisUserService.getRedisUser(id);
    }


    @PostMapping("/insert_user")
    public RedisUser insertUser(@RequestBody RedisUser redisUser) {
        redisUser = redisUserService.insertRedisUser(redisUser);
        return redisUser;
    }

    @PostMapping("/update_username")
    public Map<String, Object> updateUsername(Integer id, String username) {
        RedisUser redisUser1 = redisUserService.updateUsername(id, username);
        boolean flag = redisUser1 != null;
        String msg = flag ? "更新成功" : "更新失败";
        return resultMap(flag, msg);
    }


    @PostMapping("/delete_user")
    public Map<String, Object> deleteUser(Integer id) {
        boolean flag = redisUserService.deleteUser(id) == 1;
        String msg = flag ? "删除成功" : "删除失败";
        return resultMap(flag, msg);
    }


    private Map<String, Object> resultMap(boolean success, String msg) {
        Map<String, Object> result = new HashMap<>();
        result.put("success", success);
        result.put("msg", msg);
        return result;
    }

}

