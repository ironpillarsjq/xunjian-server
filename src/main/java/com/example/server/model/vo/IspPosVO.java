package com.example.server.model.vo;

import lombok.Data;

import java.util.Date;

@Data
public class IspPosVO {
    private String IMEI;
    private Date time;
    private double lng;
    private double lat;
    private String phoneUserName;
    private String phoneNum;
}
