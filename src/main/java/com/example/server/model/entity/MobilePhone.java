package com.example.server.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("mobilephone")
public class MobilePhone {
    @TableId(value = "ID", type = IdType.AUTO)
    private Integer id;
    private String phoneNum;
    private String phoneUserName;

    @TableField("IMEI")
    private String imei;
    private String address;
}
