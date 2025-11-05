package com.example.server.model.request;

import lombok.Data;

@Data
public class LoginRequest {
    private String loginID; // 账号
    private String loginPwd; // 密码
}
