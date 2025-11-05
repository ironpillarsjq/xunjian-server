package com.example.server.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.springframework.cglib.core.Local;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Data
@TableName("event")
public class Event {
    @TableId(type = IdType.AUTO)
    @TableField("ID")
    private Integer id;
    @TableField("IMEI")
    private String imei;
    @TableField("Time")
    private LocalDateTime time;
    @TableField("lng")
    private Double lng;
    @TableField("lat")
    private Double lat;
    @TableField("EventID")
    private Integer eventId;
    @TableField("EventImage")
    private String eventImage;
    @TableField("ChuLiType")
    private String chuLiType;
    @TableField("BanLIYaoQiu")
    private String banLIYaoQiu;
    @TableField("JiaoBanTime")
    private LocalDateTime jiaoBanTime;
    @TableField("ChengBanDanWei")
    private String chengBanDanWei;
    @TableField("BanLiFanKui")
    private String banLiFanKui;
    @TableField("FanKuiTime")
    private LocalDateTime fanKuiTime;
    @TableField("FanKuiRenYuan")
    private String fanKuiRenYuan;
    @TableField("IfBanJie")
    private String ifBanJie;
    @TableField("content")
    private String content;
}
