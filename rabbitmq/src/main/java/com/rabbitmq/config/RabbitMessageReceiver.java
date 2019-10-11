package com.rabbitmq.config;

import com.rabbitmq.pojo.User;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @program: rabbitmq
 * @description:
 * @author: Ailuoli
 * @create: 2019-06-14 15:22
 **/

@Component
public class RabbitMessageReceiver {

    @RabbitListener(queues = {"${rabbitmq.queue.msg}"})
    public void receiveMsg(String msg)
    {
        System.out.println("收到消息：【"+msg+"】");
    }

    @RabbitListener(queues = {"${rabbitmq.queue.user}"})
    public void receiverUser(User user)
    {
        System.out.println("收到用户信息：【"+user+"】");
    }

}

