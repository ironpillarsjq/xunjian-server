package com.example.server.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import software.amazon.awssdk.services.s3.endpoints.internal.Value;

@Data
public class AddSystemUserRequest {

    // loginID：字母开头，3-15位字母/数字/下划线
    @NotBlank(message = "登录ID不能为空")
    @Pattern(regexp = "^[a-zA-Z][a-zA-Z0-9_]{2,14}$", message = "登录ID需以字母开头，3-15位字母、数字或下划线")
    private String loginID;

    // userName：3-15位字母/数字/下划线
    @NotBlank(message = "用户名不能为空")
    @Pattern(regexp = "^[a-zA-Z0-9_]{3,15}$", message = "用户名需为3-15位字母、数字或下划线")
    private String userName;

    // userRoleName：不能为空
    @NotNull(message = "用户角色不能为空")
    private Boolean isAdmin;

    // userDepartment：3-10个字符
    @NotBlank(message = "部门不能为空")
    @Size(min = 3, max = 10, message = "部门长度需为3-10个字符")
    private String userDepartment;

    // userPhoneNum：11位数字
    @NotBlank(message = "手机号不能为空")
    @Pattern(regexp = "^\\d{11}$", message = "手机号必须为11位数字")
    private String userPhoneNum;
}
