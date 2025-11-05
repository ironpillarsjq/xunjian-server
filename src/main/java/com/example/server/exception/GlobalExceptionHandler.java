package com.example.server.exception;

import com.example.server.model.vo.ResponseResult;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 处理业务异常
    @ExceptionHandler(AppException.class)
    public <T> ResponseResult<T> handleAppException(AppException e) {
        return ResponseResult.error(e.getCode(), e.getMsg());
    }

    // 处理 @Valid 参数校验失败异常（DTO）
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public <T> ResponseResult<T> handleValidException(MethodArgumentNotValidException e) {
        String msg = e.getBindingResult().getFieldError().getDefaultMessage();
        return ResponseResult.error(400, msg);
    }

    // 处理 @Validated 校验失败异常（方法参数）
    @ExceptionHandler(ConstraintViolationException.class)
    public <T> ResponseResult<T> handleConstraintViolationException(ConstraintViolationException e) {
        String msg = e.getConstraintViolations().iterator().next().getMessage();
        return ResponseResult.error(400, msg);
    }

    // 处理 JSON 解析错误（缺少字段、格式错误）
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public <T> ResponseResult<T> handleHttpMessageNotReadable(HttpMessageNotReadableException e) {
        return ResponseResult.error(400, "缺少参数或参数格式错误");
    }

    // 处理 URL 不存在
    @ExceptionHandler(NoResourceFoundException.class)
    public <T> ResponseResult<T> handleNoResourceFound(NoResourceFoundException e) {
        return ResponseResult.error(404, "请求地址不存在");
    }

    // 兜底异常
    @ExceptionHandler(Exception.class)
    public <T> ResponseResult<T> handleException(Exception e) {
        e.printStackTrace();
        return ResponseResult.error(500, "系统内部错误：" + e.getMessage());
    }
}
