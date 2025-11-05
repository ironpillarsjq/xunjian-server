package com.example.server.service.impl;

import com.example.server.model.entity.SysUser;
import com.example.server.enums.AppExceptionCodeMsg;
import com.example.server.exception.AppException;
import com.example.server.mapper.SysUserMapper;
import com.example.server.utils.JWTUtil;
import com.example.server.model.vo.LoginVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Value("${app.login.expire-time}")
    private int EXPIRE_TIME;

    @Autowired
    SysUserMapper sysUserMapper;

    @Autowired
    SystemService systemService;

    @Autowired
    JWTUtil jwtUtil;

    private final String ROLE_USER = "普通用户";
    private final String ROLE_ADMIN = "系统管理员";

    /**
     * 通过id、密码登录
     *
     * @return 登录成功则返回token
     */
    public LoginVO login(String loginID, String loginPwd) {
        // 判断是管理员还是用户
        Boolean isAdmin;


        if (sysUserMapper.existsByLoginID(loginID)) {
            // 校验密码是否正确
            String password = sysUserMapper.getPasswordByLoginID(loginID);
            SysUser sysUser = sysUserMapper.getSysUserDOByLoginID(loginID);

            if (!password.equals(loginPwd)) {
                // 密码错误，登陆失败
                throw new AppException(AppExceptionCodeMsg.INVALID_PASSWORD);
            } else {
                isAdmin = sysUser.getUserRoleName().equals(ROLE_ADMIN);
                return new LoginVO(jwtUtil.createToken(String.valueOf(sysUser.getId())), isAdmin);
            }
        }
        throw new AppException(AppExceptionCodeMsg.USER_NOT_FOUND); // 用户不存在
    }




}
