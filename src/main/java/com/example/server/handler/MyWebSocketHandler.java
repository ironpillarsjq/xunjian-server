package com.example.server.handler;

import com.example.server.manager.WebSocketSessionManager;
import com.example.server.model.bo.LoginBO;
import com.example.server.security.context.UserContext;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

// -------------------------使用websocket拦截器

public class MyWebSocketHandler extends TextWebSocketHandler {
    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        // 连接建立后，可以获取用户 ID
        Integer userId = (Integer) session.getAttributes().get("id");
        WebSocketSessionManager.add(userId, session);
        System.out.println("用户连接成功: " + userId);
    }

    @Override
    protected void handleTextMessage(@NotNull WebSocketSession session, TextMessage message) throws Exception {
        System.out.println("收到消息: " + message.getPayload());
    }

    @Override
    public void afterConnectionClosed(@NotNull WebSocketSession session, CloseStatus status) {
        Integer userId = (Integer) session.getAttributes().get("id");
        WebSocketSessionManager.remove(userId);
    }
}
