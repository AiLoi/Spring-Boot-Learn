package com.activemq.controller;

import com.activemq.pojo.User;
import com.activemq.service.ActiveMqService;
import com.activemq.service.ActiveMqUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @program: activemq
 * @description:
 * @author: Ailuoli
 * @create: 2019-06-13 19:51
 **/
@RestController
@RequestMapping("/activemq")
public class ActiveMqController {

    @Autowired
    private ActiveMqService activeMqService;


    @Autowired
    private ActiveMqUserService activeMqUserService;


    @GetMapping("/msg")
    public Map<String, Object> msg(String message) {
        activeMqService.sendMsg(message);
        return result(true,message);
    }


    @PostMapping("/user")
    public Map<String,Object> sendUser(@RequestBody User user)
    {
        activeMqUserService.sendUser(user);
        return result(true,user);
    }


    private Map<String, Object> result(Boolean success, Object message) {
        Map<String, Object> result = new HashMap<>();
        result.put("success", success);
        result.put("message", message);
        return result;
    }
}

