package com.rabbitmq.service;

import com.rabbitmq.pojo.User;

/**
 * @program: rabbitmq
 * @description:
 * @author: Ailuoli
 * @create: 2019-06-14 15:06
 **/
public interface RabbitMqService {


    void sendMsg(String msg);

    void sendUser(User user);

}

