package com.activemq.service;

import com.activemq.pojo.User;

/**
 * @program: activemq
 * @description:
 * @author: Ailuoli
 * @create: 2019-06-13 19:52
 **/
public interface ActiveMqUserService {

    void sendUser(User user);

    void receiveUser(User user);
}

