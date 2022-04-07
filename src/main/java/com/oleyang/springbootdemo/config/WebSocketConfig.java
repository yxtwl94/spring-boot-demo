package com.oleyang.springbootdemo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebSocketConfig {
    // 配置websocket
    @Bean
    public WebSocketServer webSocketServer() {
        return new WebSocketServer();
    }
}
