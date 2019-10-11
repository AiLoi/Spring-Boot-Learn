package com.activemq.service;

/**
 * @program: activemq
 * @description:
 * @author: Ailuoli
 * @create: 2019-06-13 19:39
 **/

public interface ActiveMqService {

    void sendMsg(String message);

    void receiveMsg(String message);

}
