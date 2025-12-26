package com.example.server.config;

import com.example.server.handler.MyWebSocketHandler;
import com.example.server.security.auth.AuthHandshakeInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {
    private final AuthHandshakeInterceptor authHandshakeInterceptor;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(myHandler(), "/ws/message")
                .addInterceptors(authHandshakeInterceptor)
                .setAllowedOrigins("*"); // 允许跨域
    }
    
    // ✅ 构造器注入（强烈推荐）
    public WebSocketConfig(AuthHandshakeInterceptor authHandshakeInterceptor) {
        this.authHandshakeInterceptor = authHandshakeInterceptor;
    }


    @Bean
    public WebSocketHandler myHandler() {
        return new MyWebSocketHandler();
    }
}
