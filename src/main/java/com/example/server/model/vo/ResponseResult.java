package com.example.server.model.vo;

import com.example.server.enums.AppExceptionCodeMsg;
import lombok.Getter;

@Getter
public class ResponseResult<T> {

    private final int code;
    private final String msg;
    private final T data;

    private ResponseResult(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static <T> ResponseResult<T> success(T data) {
        return new ResponseResult<>(200, "success", data);
    }

    public static ResponseResult<Void> success() {
        return new ResponseResult<>(200, "success", null);
    }

    public static <T> ResponseResult<T> success(String msg, T data) {
        return new ResponseResult<>(200, msg, data);
    }

    public static <T> ResponseResult<T> error(AppExceptionCodeMsg appExceptionCodeMsg) {
        return new ResponseResult<>(appExceptionCodeMsg.getCode(), appExceptionCodeMsg.getMsg(), null);
    }

    public static <T> ResponseResult<T> error(int code, String msg) {
        return new ResponseResult<>(code, msg, null);
    }

}