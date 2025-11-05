package com.example.server.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JWTUtil {

    private final String USER_ID_KEY = "userId";

    @Value("${app.login.expire-time}")
    private long VALID_HOURS;

    @Value("${app.jwt.key}")
    private String JWT_KEY;

    public String createToken(String userId) {
        long nowMillis = System.currentTimeMillis();
        long dayMillis = this.VALID_HOURS * 60 * 60 * 1000;
        long validMillis = nowMillis + dayMillis;
        Date date = new Date(validMillis);
        return JWT.create()
                .withClaim(USER_ID_KEY, userId)
                .withExpiresAt(date)
                .sign(Algorithm.HMAC256(JWT_KEY));
    }

    public String getId(String token) {
        DecodedJWT decodedJWT = verifyAndDecode(token);
        return decodedJWT.getClaim(this.USER_ID_KEY).asString();
    }

    /**
     * 验证JWT是否有效（包括签名和过期时间）
     * @param token JWT令牌
     * @return true如果有效，false如果无效
     */
    public boolean isValid(String token) {
        try {
            verifyAndDecode(token);
            return true;
        } catch (JWTVerificationException e) {
            return false;
        }
    }

    /**
     * 验证并解码JWT
     * @param token JWT令牌
     * @return 解码后的JWT
     * @throws JWTVerificationException 如果验证失败
     */
    private DecodedJWT verifyAndDecode(String token) throws JWTVerificationException {
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(this.JWT_KEY)).build();
        return jwtVerifier.verify(token);
    }

    /**
     * 验证JWT（如果无效会抛出异常）
     * @param token JWT令牌
     * @throws JWTVerificationException 如果验证失败
     */
    public void verify(String token) throws JWTVerificationException {
        verifyAndDecode(token);
    }

    /**
     * 检查JWT是否过期
     * @param token JWT令牌
     * @return true如果已过期，false如果未过期
     */
    public boolean isTokenExpired(String token) {
        try {
            DecodedJWT decodedJWT = verifyAndDecode(token);
            return decodedJWT.getExpiresAt().before(new Date());
        } catch (JWTVerificationException e) {
            return true;
        }
    }
}