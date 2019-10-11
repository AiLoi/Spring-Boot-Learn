package com.activemq;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ActivemqApplication {

    @Bean
    public ActiveMQQueue queue() {
        return new ActiveMQQueue("test");
    }

    public static void main(String[] args) {
        SpringApplication.run(ActivemqApplication.class, args);
    }

}
