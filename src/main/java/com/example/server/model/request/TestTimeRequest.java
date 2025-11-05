package com.example.server.model.request;

import com.example.server.validation.PhoneNumberValidator;
import lombok.Data;
import com.example.server.validation.PhoneNumberValidator;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TestTimeRequest {
    private LocalDateTime testTime;
}
