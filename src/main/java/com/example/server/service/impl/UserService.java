package com.example.server.service.impl;

import com.example.server.mapper.*;
import com.example.server.model.entity.Event;
import com.example.server.model.entity.EventType;
import com.example.server.model.entity.MobilePhone;
import com.example.server.model.entity.XunJianPoint;
import com.example.server.enums.AppExceptionCodeMsg;
import com.example.server.exception.AppException;
import com.example.server.model.request.ToFeedbackIncidentRequest;
import com.example.server.model.vo.IncidentDetailVO;
import com.example.server.model.vo.IncidentRecordVO;
import com.example.server.model.vo.IspPosVO;
import com.example.server.model.vo.ResponseResult;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Service
public class UserService {

    @Autowired
    SysUserMapper sysUserMapper;
    @Autowired
    SystemService systemService;
    @Autowired
    XunJianPointMapper xunJianPointMapper;
    @Autowired
    MobilePhoneMapper mobilePhoneMapper;
    @Autowired
    private EventMapper eventMapper;
    @Autowired
    private EventTypeMapper eventTypeMapper;

    public ResponseResult<Void> changePassword(String oldPassword, String newPassword) {
        if (sysUserMapper.checkPassword(systemService.getId(), oldPassword)) {
            sysUserMapper.setPassword(systemService.getId(), newPassword);
            return ResponseResult.success(null);
        } else {
            throw new AppException(AppExceptionCodeMsg.INVALID_PASSWORD);
        }
    }

    /**
     *
     * @param ispPosValidTime 位置有效的时间
     * @return 返回的是当前正在巡检的人员位置+人员信息
     */
    public List<IspPosVO> getCurIspPosListAndInfo(int ispPosValidTime) {
        List<IspPosVO> ispPosVOList = xunJianPointMapper.getNewestIspPosList();
        Date now = new Date();
        long validTime = now.getTime() - (long) ispPosValidTime * 60 * 1000;
        ispPosVOList.removeIf(item -> item.getTime().getTime() < validTime);

        // 通过IMEI码补充名字、电话信息
        for (int i = 0; i < ispPosVOList.size(); i++) {
            MobilePhone mobilePhone = mobilePhoneMapper.getMobilePhoneUserByIMEI(ispPosVOList.get(i).getIMEI());
            if (mobilePhone == null) continue;
            IspPosVO ispPosVO =  ispPosVOList.get(i);
            ispPosVO.setPhoneNum(mobilePhone.getPhoneNum());
            ispPosVO.setPhoneUserName(mobilePhone.getPhoneUserName());
            ispPosVOList.set(i, ispPosVO);
        }

        return ispPosVOList;
    }


    public List<XunJianPoint> getMobilePhoneUserIspPosReplayingList(long startTimeStamp, long endTimeStamp, Integer id) {
        String IMEI = mobilePhoneMapper.getMobilePhoneUserById(id).getImei();
        return xunJianPointMapper.getMobilePhoneUserIspPosReplaying(startTimeStamp, endTimeStamp, IMEI);
    }

    public List<MobilePhone> getMobilePhoneUserList() {
        return mobilePhoneMapper.getMobilePhoneUserList();
    }

    public List<Event> getIncidentsList(@NotNull LocalDateTime startTime, @NotNull LocalDateTime endTime, @NotNull String incidentsState) {
        if ("全部".equals(incidentsState)) {
            return eventMapper.selectByTime(startTime, endTime);
        } else {
            List<Event> events = eventMapper.selectByTimeAndState(startTime, endTime, incidentsState);
            System.out.println(events);
            return eventMapper.selectByTimeAndState(startTime, endTime, incidentsState);
        }
    }


    public List<String> getIncidentsTypeList() {
        return eventTypeMapper.selectEventType();
    }

    public List<String> getIncidentsStates() {
        return eventMapper.incidentStates();
    }

    public List<IncidentRecordVO> getIncidentRecordList(LocalDateTime startTime, LocalDateTime endTime, String incidentState, String incidentType) {
        // 首先需要获得事件的id列表，通过事件类型
        if (incidentState != null && incidentState.isEmpty()) {
            incidentState = null;
        }
        if (incidentType != null && incidentType.isBlank()) {
            incidentType = null;
        }

        // 判断用户的身份，不同的身份返回不同的记录
        if (systemService.isAdmin()) {
//            List<Integer> incidentIdList = eventTypeMapper.selectIdByEventType(incidentType);
            List<IncidentRecordVO> list = eventMapper.getIncidentRecordList(startTime, endTime, incidentState, incidentType);
            return list;
        } else {
            Integer userId = systemService.getId();
            List<IncidentRecordVO> list = eventMapper.getDepartmentIncidentRecordList(startTime, endTime, incidentState, incidentType, userId);
            return list;
        }
    }

    public void deleteEventById(@NotNull Integer id) {
        eventMapper.deleteById(id);
    }

    public IncidentDetailVO getIncidentDetail(@NotNull Integer id) {
        return eventMapper.getIncidentDetail(id);
    }

    public void toFeedbackIncident(@Valid ToFeedbackIncidentRequest toFeedbackIncidentRequest) {
        Event event = new Event();
        event.setId(toFeedbackIncidentRequest.getId());
        event.setBanLiFanKui(toFeedbackIncidentRequest.getBanLiFanKui());
        event.setIfBanJie("已办结");
        eventMapper.insertOrUpdate(event);
    }
}
