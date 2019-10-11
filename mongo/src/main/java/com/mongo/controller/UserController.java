package com.mongo.controller;

import com.mongo.pojo.MongoUser;
import com.mongo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @program: mongo
 * @description:
 * @author: Ailuoli
 * @create: 2019-06-13 16:35
 **/
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/insert")
    public Map<String,Object> insertUser(@RequestBody MongoUser mongoUser) {
        userService.saveUser(mongoUser);

        return null;
    }

}

