package com.async.service.impl;

import com.async.service.ScheduleService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * @program: async
 * @description:
 * @author: Ailuoli
 * @create: 2019-06-14 16:05
 **/
@Service
public class ScheduleServiceImpl implements ScheduleService {

    private int count1 = 1;

    private int count2 = 1;

    private int count3 = 1;

    private int count4 = 1;


    @Override
    @Async
    @Scheduled(fixedRate = 1000)
    public void job1() {
        System.out.println("【"+Thread.currentThread().getName()+"】"+"【job1】每秒钟执行一次，执行第【"+count1+"】次");
        count1++;
    }

    @Override
    @Async
    @Scheduled(fixedRate = 1000)
    public void job2() {
        System.out.println("【"+Thread.currentThread().getName()+"】"+"【job2】每秒钟执行一次，执行第【"+count2+"】次");
        count2++;
    }

    @Override
    @Async
    @Scheduled(initialDelay = 3000,fixedRate = 1000)  //表示SpringIoC容器初始化后，第一次延迟3秒，每个一秒执行一次
    public void job3() {
        System.out.println("【"+Thread.currentThread().getName()+"】"+"【job3】每秒钟执行一次，执行第【"+count3+"】次");
        count3++;
    }

    @Override
    @Async
    @Scheduled(cron = "0 * 11 * * ?")                 //表示11：00 到11：59每分钟执行一次
    public void job4() {
        System.out.println("【"+Thread.currentThread().getName()+"】"+"【job4】每秒钟执行一次，执行第【"+count4+"】次");
        count4++;
    }
}

