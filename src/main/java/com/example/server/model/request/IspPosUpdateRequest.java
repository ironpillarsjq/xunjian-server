package com.example.server.model.request;

import com.example.server.security.validation.ImeiValidator;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Data
public class IspPosUpdateRequest {
    private Integer id;
    @ImeiValidator
    private String imei;
    @NotNull
    private Long time;
    @NotNull
    private Double lng;
    @NotNull
    private Double lat;

}
