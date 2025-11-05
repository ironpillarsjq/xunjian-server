package com.example.server.service.impl;

import com.example.server.security.context.UserContext;
import org.springframework.stereotype.Service;

@Service
public class UpdateService {
    public void rcvIspPosUpdate() {
        // 处理巡检位置信息的更新

    }

    public void ntcIspPosUpdate() {
        // 用来通知其他服务器，巡检位置已更新
    }
}
