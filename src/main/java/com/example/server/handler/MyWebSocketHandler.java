package com.example.server.handler;

import com.example.server.manager.WebSocketSessionManager;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
public class MyWebSocketHandler extends TextWebSocketHandler {

    Logger logger = LoggerFactory.getLogger(MyWebSocketHandler.class);

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        // 连接建立后，可以获取用户 ID
        String userId = (String) session.getAttributes().get("id");
        WebSocketSessionManager.add(userId, session);
        logger.info("用户ws连接成功，用户id：{}", userId);
    }

    @Override
    protected void handleTextMessage(@NotNull WebSocketSession session, TextMessage message) throws Exception {
        System.out.println("收到消息: " + message.getPayload());
    }

    @Override
    public void afterConnectionClosed(@NotNull WebSocketSession session, CloseStatus status) {
        String userId = (String) session.getAttributes().get("id");
        WebSocketSessionManager.remove(userId);
        logger.info("用户ws连接断开，用户id：{}", userId);
    }
}
