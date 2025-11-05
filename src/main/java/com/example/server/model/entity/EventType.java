package com.example.server.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("eventtype")
@Data
public class EventType {
    @TableId(type = IdType.AUTO)
    @TableField("ID")
    private Integer id;
    @TableField("eventtype")
    private String eventType;
    @TableField("eventcontent")
    private String eventContent;
}
