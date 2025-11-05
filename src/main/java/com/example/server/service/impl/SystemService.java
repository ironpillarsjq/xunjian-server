package com.example.server.service.impl;

import com.example.server.model.bo.LoginBO;
import com.example.server.enums.AppExceptionCodeMsg;
import com.example.server.exception.AppException;
import com.example.server.security.context.UserContext;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
/**
 * 用于获取系统信息，如当前访问的用户身份信息等。
 */
public class SystemService {


    public boolean isAdmin() {
        LoginBO user = UserContext.getUser();
        if (user == null) {
            LoggerFactory.getLogger(SystemService.class).error("user为空");
            return false;
        } else {
            return user.isAdmin();
        }
    }

    /**
     * 获取用户的LoginBO
     */
    public LoginBO getLoginBO() {
       return UserContext.getUser();
    }

    public Integer getId() {
        return getLoginBO().getId();
    }

    public void curIsAdmin() {
        if (!isAdmin()) {
            throw new AppException(AppExceptionCodeMsg.INVALID_ROLE);
        }
    }

}
