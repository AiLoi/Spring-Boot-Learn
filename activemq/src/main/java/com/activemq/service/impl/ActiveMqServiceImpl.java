package com.activemq.service.impl;

import com.activemq.service.ActiveMqService;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

/**
 * @program: activemq
 * @description:
 * @author: Ailuoli
 * @create: 2019-06-13 19:40
 **/

@Service
public class ActiveMqServiceImpl implements ActiveMqService {

    private final JmsTemplate jmsTemplate;

    public ActiveMqServiceImpl(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }


    @Override
    public void sendMsg(String message) {
        System.out.println("发送消息【"+message+"】");
        jmsTemplate.convertAndSend(message);
        //自定义发送地址
        //jmsTemplate.convertAndSend("your-destination",message);
    }

    @Override
    @JmsListener(destination = "${spring.jms.template.default-destination}")
    public void receiveMsg(String message) {
        System.out.println("接收到消息：【"+message+"】");
    }


}

