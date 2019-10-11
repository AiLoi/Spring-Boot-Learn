package com.websocket.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * @program: websocket
 * @description: 自定义websocket服务端点配置
 * @author: Ailuoli
 * @create: 2019-06-15 18:48
 **/

@Configuration
public class WebSocketConfig {


    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }
}

