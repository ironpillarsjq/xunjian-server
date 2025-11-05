package com.example.server.model.vo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class IncidentDetailVO {
    // 基本信息
    private Integer id;
    private String imei;
    private String phoneNum;
    private String phoneUserName;
    private LocalDateTime time;
    private Double lng;
    private Double lat;
    private String address;
    private String eventType;
    private String eventContent;
    private String content; // 事件描述
    private String eventImage;

    // 交办信息
    private String chuLiType;
    private String banLIYaoQiu;
    private LocalDateTime jiaoBanTime;
    private String chengBanDanWei;
    private String ifBanJie; // 是否办结

    //处理反馈
    private String banLiFanKui;
    private LocalDateTime fanKuiTime;
    private String fanKuiRenYuan;

}
