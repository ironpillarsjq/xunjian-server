package com.example.server.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("test")
public class Test {
    @TableField("test_time")
    private LocalDateTime testTime;
}
