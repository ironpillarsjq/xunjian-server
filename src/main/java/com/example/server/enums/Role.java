package com.example.server.enums;

import lombok.Getter;

@Getter
public enum Role {

    ADMIN("系统管理员");
    private final String roleName;

    Role(String roleName) {
        this.roleName = roleName;
    }
}
