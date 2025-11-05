package com.example.server.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ToFeedbackIncidentRequest {
    @NotNull
    private Integer id;
    @NotNull
    private String banLiFanKui;
}
