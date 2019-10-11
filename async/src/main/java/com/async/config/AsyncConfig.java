package com.async.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

/**
 * @program: async
 * @description: 异步线程池配置
 * @author: Ailuoli
 * @create: 2019-06-14 11:34
 **/
@Configuration
@EnableAsync       //开启异步
@EnableScheduling  //开启定时机制
public class AsyncConfig implements AsyncConfigurer {

    @Override
    public Executor getAsyncExecutor() {

        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();

        taskExecutor.setCorePoolSize(10);

        taskExecutor.setMaxPoolSize(30);

        taskExecutor.setQueueCapacity(2000);

        taskExecutor.initialize();

        return taskExecutor;
    }

}

