package com.example.server.model.entity;

import lombok.Data;

@Data
public class SysUser {
    private Integer id;
    private String loginID;
    private String loginPwd;
    private String userName;
    private String userRoleName;
    private String userDepartment;
    private String userPhoneNum;
}
