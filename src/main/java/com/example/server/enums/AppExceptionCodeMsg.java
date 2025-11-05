package com.example.server.enums;

import lombok.Getter;

@Getter
public enum AppExceptionCodeMsg {

    //通用
    MISSING_PARAMETER(10000, "缺少参数"),

    //用户
    AUTH_TOKEN_EXPIRED(11000, "登录过期"),
    USER_NOT_FOUND(11001, "用户不存在"),
    INVALID_PASSWORD(11002, "密码错误"),
    ACCOUNT_LOCKED(11005, "账号被锁定"),
    INVALID_TEMPORARY_TOKEN(11006, "TemporaryToken错误"),
    INVALID_TOKEN(11007, "token错误"),
    INVALID_ROLE(11008, "非法用户"),
    USER_ALREADY_REGISTERED(11009, "用户已注册"),
    IMEI_ALREADY_EXITS(11009, "IMEI已经存在"),

    //文件
    FILE_NOT_FOUND(12000, "不存在该文件"),
    PERMISSION_DENIED(12001, "无访问权限"),

    // 文章
    USER_DONT_HAVE_THIS_ESSAY(13000, "用户没有该文章"),
    ESSAY_NOT_FOUND(13001, "文章不存在"),


    //内部错误(异常)
    MINIO_FILE_DELETE_ERROR(20000, "MinIO删除文件错误"),
    MINIO_FILE_GET_UPLOAD_URL_ERROR(20001, "MinIO获取文件上传url错误"),
    MINIO_FILE_GET_DOWNLOAD_URL_ERROR(20002, "MinIO获取文件下载url错误"),
    MINIO_FILE_UPLOAD_ERROR(20003, "MinIO上传文件错误"),
    MINIO_FILE_DOWNLOAD_ERROR(20004, "MinIO下载文件错误"),

    S3_IMAGE_GET_UPLOAD_URL_ERROR(20005, "S3获取照片上传url错误"),
    S3_IMAGE_UPLOAD_ERROR(20006, "S3上传照片错误"),
    S3_IMAGE_DOWNLOAD_ERROR(20007, "S3下载照片错误"),
    S3_IMAGE_DELETE_ERROR(20008, "S3删除照片错误"),

    // JWT
    TOKEN_EXPIRED(21000, "JWT过期"), INVALID_FILENAME(21001, "文件名无效！"), FAIL_UPLOAD(21002, "上传失败: "), INVALID_DELETE(20200, "不能删除自己");
    private final int code;
    private final String msg;

    AppExceptionCodeMsg(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "AppExceptionCodeMsg{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                '}';
    }
}
