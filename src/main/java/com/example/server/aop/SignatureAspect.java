package com.example.server.aop;
import com.example.server.utils.SignUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Objects;

@Aspect
@Component
@Slf4j
public class SignatureAspect {

    @Autowired
    private ObjectMapper objectMapper;

    // 实际项目中，这里应该去数据库或配置文件根据 appId 查 secret

    @Value("${internal-communication.xunjian-server-mobile.secret}")
    private String APP_SECRET;

    @Value("${internal-communication.common.relay-time-window-s}")
    private int RELAY_TIME_WINDOW_S;

    @Before("@annotation(com.example.server.annotation.CheckSignature)")
    public void verify(JoinPoint joinPoint) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();

        // 1. 获取 Header
        String appId = request.getHeader("X-App-Id");
        String timestamp = request.getHeader("X-Timestamp");
        String signFromHeader = request.getHeader("X-Sign");

        if (appId == null || timestamp == null || signFromHeader == null) {
            throw new RuntimeException("缺少签名验证参数");
        }

        // 2. 防重放攻击 (可选)：检查 timestamp 是否在当前时间 60秒内
        long clientTime = Long.parseLong(timestamp);
        if (System.currentTimeMillis() - clientTime > RELAY_TIME_WINDOW_S * 1000L) {
            throw new RuntimeException("请求已过期");
        }

        // 3. 获取 Body 数据
        // 因为 @RequestBody 已经把 JSON 转成了对象，我们可以直接从参数里拿，再转回 JSON 字符串来计算签名
        // 注意：这要求 JSON 序列化规则必须严格一致（字段顺序等），或者只签名字段值。
        // *简便做法*：直接拿 Controller 的第一个参数
        Object requestBodyObj = joinPoint.getArgs()[0];
        String jsonBody = objectMapper.writeValueAsString(requestBodyObj);

        // 4. 服务端重新计算签名
        String rawString = appId + timestamp + jsonBody;
        String calculatedSign = SignUtil.sign(rawString, APP_SECRET);

        // 5. 比对
        if (!calculatedSign.equals(signFromHeader)) {
            log.error("签名验证失败! Header: {}, ServerCalc: {}", signFromHeader, calculatedSign);
            throw new RuntimeException("签名验证失败");
        }
    }
}
