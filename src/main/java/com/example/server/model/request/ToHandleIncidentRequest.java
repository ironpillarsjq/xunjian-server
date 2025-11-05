package com.example.server.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ToHandleIncidentRequest {
    @NotNull
    private Integer id;
    @NotBlank
    private String chuLiType;
    @NotBlank
    private String chengBanDanWei;
    @NotNull
    private String banLiYaoQiu;
}
