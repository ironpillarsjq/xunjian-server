package com.example.server.model.vo;

import lombok.Data;

@Data
public class LoginVO {
    private String token;
    private Boolean isAdmin;


    public LoginVO(String token, boolean b) {
        this.token = token;
        this.isAdmin = b;
    }
}
