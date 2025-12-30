package com.example.server.model.request;

//import com.example.server.security.validation.ImeiValidator;
import com.example.server.security.validation.PhoneNumberValidator;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class EditMobileUserRequest {

    @NotNull(message = "用户ID不能为空")
    private Integer id;

    @NotBlank(message = "手机号用户名不能为空")
    private String phoneUserName;

//    @ImeiValidator
    @NotBlank
    private String imei;


    @NotBlank(message = "地址不能为空")
    private String address;

    @PhoneNumberValidator
    private String phoneNum;
}
