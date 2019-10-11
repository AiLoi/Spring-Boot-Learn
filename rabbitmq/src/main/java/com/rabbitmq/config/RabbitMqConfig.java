package com.rabbitmq.config;

import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @program: rabbitmq
 * @description: 创建队列
 * @author: Ailuoli
 * @create: 2019-06-14 14:48
 **/
@Configuration
public class RabbitMqConfig {

    @Value("${rabbitmq.queue.msg}")
    private String msgQueueName;


    @Value("${rabbitmq.queue.user}")
    private String userQueueName;


    /*
    创建字符串消息队列 ，true代表是否持久化消息
     */
    @Bean
    public Queue createQueueMsg() {
        return new Queue(msgQueueName,true);
    }

    /*
    创建用户消息队列
     */
    @Bean
    public Queue createQueueUser() {
        return new Queue(userQueueName,true);
    }
}

