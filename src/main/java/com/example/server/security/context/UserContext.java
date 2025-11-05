package com.example.server.security.context;

import com.example.server.model.bo.LoginBO;

public class UserContext {
    private static final ThreadLocal<LoginBO> currentUser = new ThreadLocal<>();

    public static void setUser(LoginBO user) {
        currentUser.set(user);
    }

    public static LoginBO getUser() {
        return currentUser.get();
    }

    public static void clear() {
        currentUser.remove();
    }
}