package com.example.server.model.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ResetSystemUserPasswordRequest {
    @NotBlank(message = "用户名不可为空")
    private String loginID;
}
