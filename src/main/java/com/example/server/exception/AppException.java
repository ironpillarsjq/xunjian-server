package com.example.server.exception;

import com.example.server.enums.AppExceptionCodeMsg;
import lombok.Getter;

@Getter
public class AppException extends RuntimeException {
    private final int code;
    private final String msg;

    public AppException(AppExceptionCodeMsg appExceptionCodeMsg) {
        super();

        this.code = appExceptionCodeMsg.getCode();
        this.msg = appExceptionCodeMsg.getMsg();
    }

    public AppException(int code, String msg) {
        super();
        this.code = code;
        this.msg = msg;

    }

    public AppException(String msg) {
        super();
        this.code = 30000;
        this.msg = msg;
    }

}
