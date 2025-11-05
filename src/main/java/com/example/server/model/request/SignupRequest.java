package com.example.server.model.request;

import com.example.server.validation.PhoneNumberValidator;
import lombok.Data;

@Data
public class SignupRequest {
    private String loginID;
    private String loginPwd;
    private String userName;
    private String userRoleName;
    private String userDepartment;
    @PhoneNumberValidator
    private String userPhoneNum;
}
