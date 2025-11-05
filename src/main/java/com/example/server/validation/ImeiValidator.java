package com.example.server.validation;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.ReportAsSingleViolation;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import java.lang.annotation.*;

/**
 * 手机号校验注解（无需自定义逻辑类）
 */
@NotBlank
@Pattern(regexp = "^[A-Za-z0-9]{15}$")
@ReportAsSingleViolation  // 只返回一个错误信息
@Constraint(validatedBy = {}) // ✅ 这里留空，表示不需要自定义校验器
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ImeiValidator {
    String message() default "IMEI需要15数字或字母组成";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

