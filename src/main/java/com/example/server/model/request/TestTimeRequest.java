package com.example.server.model.request;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TestTimeRequest {
    private LocalDateTime testTime;
}
