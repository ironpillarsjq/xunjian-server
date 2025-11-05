package com.example.server.model.bo;

import com.example.server.model.entity.SysUser;
import com.example.server.enums.Role;
import lombok.Getter;

@Getter
public class LoginBO {

    private final Integer id;
    private final String loginID;
    private final String loginPwd;
    private final String userName;
    private final String userRoleName;
    private final String userDepartment;
    private final String userPhoneNum;
    private final Boolean isAdmin;

    public LoginBO(SysUser sysUser) {
        this.id = sysUser.getId();
        this.loginID = sysUser.getLoginID();
        this.loginPwd = null;
        this.userName = sysUser.getUserName();
        this.userRoleName = sysUser.getUserRoleName();
        this.userDepartment = sysUser.getUserDepartment();
        this.userPhoneNum = sysUser.getUserPhoneNum();
        this.isAdmin = sysUser.getUserRoleName().equals(Role.ADMIN.getRoleName());
    }

    public Boolean isAdmin() {
        return isAdmin;
    }
}
