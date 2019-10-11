package com.rabbitmq.controller;

import com.rabbitmq.pojo.User;
import com.rabbitmq.service.RabbitMqService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @program: rabbitmq
 * @description:
 * @author: Ailuoli
 * @create: 2019-06-14 15:26
 **/
@RestController
@RequestMapping("/rabbitmq")
public class RabbitMqController {

    @Autowired
    private RabbitMqService rabbitMqService;

    @GetMapping("/msg")
    public Map<String, Object> msg(String message) {
        rabbitMqService.sendMsg(message);
        return resultMap("message", message);
    }

    @PostMapping("/user")
    public Map<String, Object> user(@RequestBody User user) {
        rabbitMqService.sendUser(user);
        return resultMap("user", user);
    }


    private Map<String, Object> resultMap(String key, Object obj) {
        Map<String, Object> result = new HashMap<>();

        result.put("success", true);
        result.put(key, obj);

        return result;
    }
}

