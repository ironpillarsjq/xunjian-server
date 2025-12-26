package com.example.server.manager;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class WebSocketSessionManager {
    private static final Map<Integer, WebSocketSession> SESSION_POOL = new ConcurrentHashMap<>();
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final Logger logger = LoggerFactory.getLogger(WebSocketSessionManager.class);
    // 添加连接
    public static void add(Integer userId, WebSocketSession session) {
        SESSION_POOL.put(userId, session);
    }

    // 移除连接
    public static void remove(Integer userId) {
        SESSION_POOL.remove(userId);
    }

    // 主动推送消息给指定用户
    public static void sendMessage(Integer userId, Object message) {
        WebSocketSession session = SESSION_POOL.get(userId);
        if (session != null && session.isOpen()) {
            try {
                session.sendMessage(new TextMessage(objectMapper.writeValueAsString(message)));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // 主动推送消息给所有用户
    public static void sendMessageToAll(Object message) {
        if (SESSION_POOL.isEmpty()) {
            return;
        }

        try {
            // 1. 先序列化一次，避免在循环中重复序列化消耗性能
            String jsonMessage = objectMapper.writeValueAsString(message);
            TextMessage textMessage = new TextMessage(jsonMessage);

            // 2. 遍历 Session 池
            SESSION_POOL.forEach((userId, session) -> {
                try {
                    if (session.isOpen()) {
                        session.sendMessage(textMessage);
                        logger.info("已向用户 {} 推送广播消息", userId);
                    } else {
                        // 如果发现连接已关闭，顺手清理掉
                        SESSION_POOL.remove(userId);
                    }
                } catch (IOException e) {
                    logger.error("向用户 {} 发送广播失败: {}", userId, e.getMessage());
                }
            });
        } catch (Exception e) {
            logger.error("广播消息序列化失败", e);
        }
    }

}
