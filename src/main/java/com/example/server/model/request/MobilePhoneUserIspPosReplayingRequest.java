package com.example.server.model.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data

public class MobilePhoneUserIspPosReplayingRequest {
    @NotNull
    private Long startTimeStamp;
    @NotNull
    private Long endTimeStamp;
    @NotNull
    private Integer id;
}
