package com.example.server.model.vo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class IncidentRecordVO {
    private String id;
    private String imei;
    private String phoneUserName;
    private String phoneNum;
    private LocalDateTime time;
    private String address;
    private String eventType;
    private String eventContent;
    private String ifBanJie;
    private String chengBanDanWei;
}
