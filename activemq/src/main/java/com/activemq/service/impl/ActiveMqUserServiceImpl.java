package com.activemq.service.impl;

import com.activemq.pojo.User;
import com.activemq.service.ActiveMqUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

/**
 * @program: activemq
 * @description:
 * @author: Ailuoli
 * @create: 2019-06-13 19:53
 **/
@Service
public class ActiveMqUserServiceImpl implements ActiveMqUserService {

    @Autowired
    private JmsTemplate jmsTemplate;

    private static final String myDestination = "my-destination";

    @Override
    public void sendUser(User user) {
        System.out.println("发送消息【"+user+"】");
        jmsTemplate.convertAndSend(myDestination,user);
    }

    @Override
    @JmsListener(destination = myDestination)
    public void receiveUser(User user) {
        System.out.println("接收到消息：【"+user+"】");
    }
}

