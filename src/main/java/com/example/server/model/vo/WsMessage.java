package com.example.server.model.vo;

import lombok.Data;

// 业务消息实体
@Data
public class WsMessage {
    private String type;    // 消息类型：ORDER_PAYED, SYSTEM_NOTICE 等
    private Object data;    // 真正的数据内容

    public WsMessage(String type, Object data) {
        this.type = type;
        this.data = data;
    }
}