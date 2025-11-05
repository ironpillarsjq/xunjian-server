package com.example.server.security.auth;

import com.example.server.model.entity.SysUser;
import com.example.server.model.bo.LoginBO;
import com.example.server.mapper.SysUserMapper;
import com.example.server.security.context.UserContext;
import com.example.server.utils.JWTUtil;
import com.google.gson.Gson;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.util.ContentCachingRequestWrapper;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class JwtAuthFilter implements Filter {

    @Autowired
    private JWTUtil jwtUtil;

    Logger logger = LoggerFactory.getLogger(JwtAuthFilter.class);

    @Autowired
    private SysUserMapper adminMapper;  // 你的管理员Mapper
    // 定义不需要认证的路径
    private static final List<String> EXCLUDED_PATHS = Arrays.asList(
            "/user/login"
    );

    private final Gson gson = new Gson();
    @Autowired
    private SysUserMapper sysUserMapper;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {



        logger.info("进入JwtAuthFilter");

        // 包装请求以保护原始Content-Type
        ContentCachingRequestWrapper wrappedRequest = new ContentCachingRequestWrapper((HttpServletRequest) request);
        // 获取原始Content-Type
        String contentType = wrappedRequest.getContentType();

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        String path = httpRequest.getRequestURI();
        // 处理 OPTIONS 预检请求
        if ("OPTIONS".equalsIgnoreCase(httpRequest.getMethod())) {
            logger.info("处理 OPTIONS 预检请求: {}", path);
            chain.doFilter(request, response); // 直接放行
            return;
        }

        // 排除登录、注册等不需要认证的接口
        if (isExcludedPath(path)) {
            chain.doFilter(request, response);
            return;
        }

        try {
            String token = extractToken(httpRequest);

            if (token != null && jwtUtil.isValid(token)) {
                // 从JWT中获取用户ID
                Integer id = Integer.parseInt(jwtUtil.getId(token));

                // 查询数据库获取完整用户信息
                LoginBO loginBO = loadUserFromDatabase(id);

                if (loginBO != null) {
                    // 存入ThreadLocal上下文
                    UserContext.setUser(loginBO);
                }
            } else {
                // 否则返回令牌非法
                sendErrorResponse(httpResponse, HttpServletResponse.SC_UNAUTHORIZED, "INVALID_TOKEN", "Invalid or missing token");
                return;
            }

            chain.doFilter(wrappedRequest, response);
        } finally {
            // 确保每次请求后清理ThreadLocal
            UserContext.clear();
        }
    }

    private String extractToken(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            return header.substring(7);
        }
        return null;
    }

    private LoginBO loadUserFromDatabase(Integer id) {
        // 先尝试从普通用户表查询
        SysUser sysUser = sysUserMapper.getSysUserDOByID(id);
        if (sysUser != null) {
            return new LoginBO(sysUser);
        }
        return null;
    }

    private boolean isExcludedPath(String path) {
        return EXCLUDED_PATHS.stream().anyMatch(excluded -> path.equals(excluded) || path.startsWith(excluded + "/"));
    }

    private void sendErrorResponse(HttpServletResponse response, int status, String code, String message) throws IOException {
        response.setStatus(status);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("code", code);
        errorResponse.put("message", message);

        response.getWriter().write(gson.toJson(errorResponse));
    }
}