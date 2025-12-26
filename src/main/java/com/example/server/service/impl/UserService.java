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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
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


    public List<List<XunJianPoint>> getMobilePhoneUserIspPosReplayingList(long startTimeStamp, long endTimeStamp, Integer id) {
        String IMEI = mobilePhoneMapper.getMobilePhoneUserById(id).getImei();
        List<XunJianPoint> allXunJianPointList = xunJianPointMapper.getMobilePhoneUserIspPosReplaying(startTimeStamp, endTimeStamp, IMEI);
        // 按照时间的间隔和距离的间隔来区分是否是同一次巡检
        List<List<XunJianPoint>> resList = new ArrayList<>();
        if (allXunJianPointList.size() == 1 || allXunJianPointList.isEmpty()) {
            resList.add(allXunJianPointList);
        } else {
            resList.add(new ArrayList<>());
            // 第一个巡检点直接加入
            resList.getLast().add(allXunJianPointList.getFirst());

            for (int i = 1; i < allXunJianPointList.size(); i++) {
                // 判断当前点与前一点是否同组
                if (sameGroupJudge(allXunJianPointList.get(i-1), allXunJianPointList.get(i))) {
                    // 同一组
                    resList.getLast().add(allXunJianPointList.get(i));
                } else {
                    // 不同组
                    // 判断上一组点的数量是否超过1，如果不到1个点，则删除
                    if (resList.getLast().size() <= 1) {
                        resList.removeLast();
                    }
                    resList.add(new ArrayList<>());
                    resList.getLast().add(allXunJianPointList.get(i));
                }

            }
        }
        return resList;
    }

    @Value("${app.xunjian-point.time-limit}")
    private int TIME_LIMIT_MINUTES;

    @Value("${app.xunjian-point.distance-limit}")
    private int DISTANCE_LIMIT_KM;

    private boolean sameGroupJudge(XunJianPoint p1, XunJianPoint p2) {
        if (p1 == null || p2 == null) {
            return false;
        }

        // 1. 时间差判断
        long timeDiff = Math.abs(p2.getTime() - p1.getTime());
        if (timeDiff > (long) TIME_LIMIT_MINUTES * 60 * 1000) {
            return false;
        }

        // 2. 距离判断
        double distance = distanceKm(
                p1.getLat(), p1.getLng(),
                p2.getLat(), p2.getLng()
        );

        return distance <= DISTANCE_LIMIT_KM;
//        return true;
    }

    private static double distanceKm(double lat1, double lon1,
                                     double lat2, double lon2) {

        final double R = 6371.0; // 地球半径（km）

        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1))
                * Math.cos(Math.toRadians(lat2))
                * Math.sin(dLon / 2)
                * Math.sin(dLon / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return R * c;
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
