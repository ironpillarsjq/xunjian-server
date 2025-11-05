package com.example.server.model.request;

import com.example.server.validation.ImeiValidator;
import com.example.server.validation.PhoneNumberValidator;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class EditMobileUserRequest {

    @NotNull(message = "用户ID不能为空")
    private Integer id;

    @NotBlank(message = "手机号用户名不能为空")
    private String phoneUserName;

    @ImeiValidator
    private String imei;


    @NotBlank(message = "地址不能为空")
    private String address;

    @PhoneNumberValidator
    private String phoneNum;
}
