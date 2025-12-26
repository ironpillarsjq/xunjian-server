package com.example.server.model.request;

import com.example.server.security.validation.PhoneNumberValidator;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class EditSystemUserRequest {
    @NotBlank(message = "用户账号不能为空")
    private String loginID; // 用户名，不可修改，用于标识用户
    @NotBlank(message = "用户姓名不能为空")
    private String userName;
    @PhoneNumberValidator
    private String userPhoneNum;
    @NotBlank(message = "用户单位不能为空")
    private String userDepartment;
}
