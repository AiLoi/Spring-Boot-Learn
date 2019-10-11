package com.async.controller;

import com.async.service.AsyncService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: async
 * @description:
 * @author: Ailuoli
 * @create: 2019-06-14 11:47
 **/
@RestController
@RequestMapping("/async")
public class AsyncController {

    @Autowired
    private AsyncService asyncService;


    @GetMapping("/page")
    public String asyncPage() {
        System.out.println("请求线程名称："+"【"+Thread.currentThread().getName()+"】");
        asyncService.generateReport();
        return "async";
    }

}

