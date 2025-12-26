package com.example.server.security.auth;

import com.example.server.utils.JWTUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;

@Component
public class AuthHandshakeInterceptor implements HandshakeInterceptor {

    @Autowired
    private JWTUtil jwtUtil;

    @Override
    public boolean beforeHandshake(
            @NotNull ServerHttpRequest request,
            @NotNull ServerHttpResponse response,
            @NotNull WebSocketHandler wsHandler,
            @NotNull Map<String, Object> attributes) {

        if (!(request instanceof ServletServerHttpRequest servletRequest)) {
            return false;
        }

        HttpServletRequest req = servletRequest.getServletRequest();

        // 1️⃣ 从 token 里解析用户（推荐）
        String token = req.getParameter("token");
        if (token != null && jwtUtil.isValid(token)) {
            String userId = jwtUtil.getId(token);
            if (userId == null) {
                return false; // ❌ 拒绝 WS 连接
            }
            // 2️⃣ 放进 WebSocketSession.attributes
            attributes.put("id", userId);
        } else {
            return false;
        }

        return true;
    }

    @Override
    public void afterHandshake(
            @NotNull ServerHttpRequest request,
            @NotNull ServerHttpResponse response,
            @NotNull WebSocketHandler wsHandler,
            Exception exception) {
    }
}