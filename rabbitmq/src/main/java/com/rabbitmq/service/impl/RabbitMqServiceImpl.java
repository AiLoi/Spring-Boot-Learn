package com.rabbitmq.service.impl;

import com.rabbitmq.pojo.User;
import com.rabbitmq.service.RabbitMqService;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @program: rabbitmq
 * @description:
 * @author: Ailuoli
 * @create: 2019-06-14 15:08
 **/
@Service
public class RabbitMqServiceImpl implements RabbitMqService, RabbitTemplate.ConfirmCallback {


    @Value("${rabbitmq.queue.msg}")
    private String msgRouting;


    @Value("${rabbitmq.queue.user}")
    private String userRouting;


    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public void sendMsg(String msg) {
        System.out.println("发送消息：【" + msg + "】");

        //设置回调
        rabbitTemplate.setConfirmCallback(this);
        //发送消息，通过msgRouting确认队列
        rabbitTemplate.convertAndSend(msgRouting, msg);

    }

    @Override
    public void sendUser(User user) {

        System.out.println("发送用户消息：【" + user + "】");

        //设置回调
        rabbitTemplate.setConfirmCallback(this);

        rabbitTemplate.convertAndSend(userRouting, user);
    }

    //回调确认方法
    @Override
    public void confirm(CorrelationData correlationData, boolean b, String s) {

        if (b) {
            System.out.println("消息成功消费：");
        } else {
            System.out.println("消息消费失败：" + s);
        }
    }
}

