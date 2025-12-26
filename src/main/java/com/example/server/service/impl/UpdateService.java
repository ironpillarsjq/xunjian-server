package com.example.server.service.impl;

import com.example.server.manager.WebSocketSessionManager;
import com.example.server.mapper.MobilePhoneMapper;
import com.example.server.model.entity.MobilePhone;
import com.example.server.model.request.IspPosUpdateRequest;
import com.example.server.model.vo.IspPosVO;
import com.example.server.security.context.UserContext;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Timestamp;

@Service
public class UpdateService {

    @Autowired
    private MobilePhoneMapper mobilePhoneMapper;

    public void ispPosUpdate(@Valid IspPosUpdateRequest ispPosUpdateRequest) {
        // 获取完整数据
        MobilePhone mobilePhone = mobilePhoneMapper.getMobilePhoneUserByIMEI(ispPosUpdateRequest.getImei());
        // 整合所有需要的数据信息
        IspPosVO ispPosVO = new IspPosVO();

        ispPosVO.setIMEI(ispPosUpdateRequest.getImei());
        ispPosVO.setTime(new Date(ispPosUpdateRequest.getTime())); // 从long时间戳转换成Date
        ispPosVO.setLat(ispPosUpdateRequest.getLat());
        ispPosVO.setLng(ispPosUpdateRequest.getLng());
        ispPosVO.setPhoneNum(mobilePhone.getPhoneNum());
        ispPosVO.setPhoneUserName(mobilePhone.getPhoneUserName());

        WebSocketSessionManager.sendMessageToAll(ispPosVO);

    }
}
