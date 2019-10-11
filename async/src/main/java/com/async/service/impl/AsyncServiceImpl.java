package com.async.service.impl;

import com.async.service.AsyncService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * @program: async
 * @description:
 * @author: Ailuoli
 * @create: 2019-06-14 11:44
 **/
@Service
public class AsyncServiceImpl implements AsyncService {

    @Override
    @Async
    public void generateReport() {
        System.out.println("报表线程名称："+"【"+Thread.currentThread().getName()+"】");
    }
}

