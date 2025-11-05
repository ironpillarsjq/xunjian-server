package com.example.server.model.entity;

import lombok.Data;

@Data
public class XunJianPoint {
    private Integer id;
    private String IMEI;
    private Long time;
    private Double lng;
    private Double lat;
}
