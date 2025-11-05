package com.example.server.model.request;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class IncidentRecordRequest {

    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String incidentState;
    private String incidentType;
}
