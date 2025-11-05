package com.example.server.controller;

import com.example.server.model.entity.Event;
import com.example.server.model.entity.EventType;
import com.example.server.model.entity.MobilePhone;
import com.example.server.model.entity.XunJianPoint;
import com.example.server.enums.AppExceptionCodeMsg;
import com.example.server.exception.AppException;
import com.example.server.model.request.*;
import com.example.server.model.vo.*;
import com.example.server.service.impl.AdminService;
import com.example.server.service.impl.AuthService;
import com.example.server.service.impl.SystemService;
import com.example.server.service.impl.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.simpleframework.xml.core.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private AdminService adminService;
    @Autowired
    private AuthService authService;
    @Autowired
    private SystemService systemService;


    @PostMapping("/login")
    public ResponseResult<LoginVO> login(@RequestBody LoginRequest loginRequest) {
        if (loginRequest.getLoginID() == null || loginRequest.getLoginPwd() == null) {
            throw new AppException(AppExceptionCodeMsg.MISSING_PARAMETER);
        }
        return ResponseResult.success(authService.login(loginRequest.getLoginID(), loginRequest.getLoginPwd()));
    }


//    @PostMapping("/signup")
//    public ResponseResult<LoginVO> signup(@RequestBody SignupRequest signupRequest) {
//        if (signupRequest.getLoginID().isEmpty() || signupRequest.getLoginPwd().isEmpty()) {
//            throw new AppException(AppExceptionCodeMsg.MISSING_PARAMETER);
//        }
//        adminService.addSysUser(signupRequest.getLoginID(), signupRequest.getLoginPwd(), signupRequest.getUserName(), signupRequest.getUserRoleName(), signupRequest.getUserDepartment(), signupRequest.getUserPhoneNum());
//        return ResponseResult.success(authService.login(signupRequest.getLoginID(), signupRequest.getLoginPwd()));
//    }

    @Operation(summary = "修改密码")
    @PostMapping("/change-password")
    public ResponseResult<Void> changePassword(@RequestBody ChangePasswordRequest changePasswordRequest) {
        // 检查参数完整性
        if (changePasswordRequest.getOldPassword().isEmpty() || changePasswordRequest.getNewPassword().isEmpty()) {
            throw new AppException(AppExceptionCodeMsg.MISSING_PARAMETER);
        }

        // 修改密码
        return userService.changePassword(changePasswordRequest.getOldPassword(), changePasswordRequest.getNewPassword());
    }

    @Value("${app.user.isp-pos-valid-time}")
    private int ISP_POS_VALID_TIME;

    @Operation(summary = "获取当前巡检人员的定位信息")
    @GetMapping("/get-cur-isp-pos-list")
    public ResponseResult<List<IspPosVO>> getCurIspPosList() {
        // 返回当前人员的定位信息列表
        return ResponseResult.success(userService.getCurIspPosListAndInfo(ISP_POS_VALID_TIME));
    }

    @Operation(summary = "人员GPS回演")
    @PostMapping("/get-mobile-phone-user-isp-pos-replaying-list")
    public ResponseResult<List<XunJianPoint>> getMobilePhoneUserIspPosReplayingList(@RequestBody MobilePhoneUserIspPosReplayingRequest mobilePhoneUserIspPosReplayingRequest) {
        return ResponseResult.success(userService.getMobilePhoneUserIspPosReplayingList(mobilePhoneUserIspPosReplayingRequest.getStartTimeStamp(), mobilePhoneUserIspPosReplayingRequest.getEndTimeStamp(), mobilePhoneUserIspPosReplayingRequest.getId()));
    }

    @Operation(summary = "获取突发事件")
    @PostMapping("/get-incidents-list")
    public ResponseResult<List<Event>> getIncidentsList(@RequestBody IncidentsListRequest incidentsListRequest) {
        return ResponseResult.success(userService.getIncidentsList(incidentsListRequest.getStartTime(), incidentsListRequest.getEndTime(), incidentsListRequest.getIncidentsState()));
    }

    @Operation(summary = "获取突发事件记录")
    @PostMapping("/get-incident-record-list")
    public ResponseResult<List<IncidentRecordVO>> getIncidentRecordList(@RequestBody IncidentRecordRequest incidentRecordRequest) {

        return ResponseResult.success(userService.getIncidentRecordList(incidentRecordRequest.getStartTime(), incidentRecordRequest.getEndTime(), incidentRecordRequest.getIncidentState(), incidentRecordRequest.getIncidentType()));

    }

    @Operation(summary = "获取突发事件类型")
    @GetMapping("/get-incidents-type-list")
    public ResponseResult<List<String>> getIncidentsTypeList() {
        return ResponseResult.success(userService.getIncidentsTypeList());
    }

    @Operation(summary = "获取事件办理的状态")
    @GetMapping("/get-incidents-states")
    public ResponseResult<List<String>> getIncidentsStates() {
        return ResponseResult.success(userService.getIncidentsStates());
    }

    @Operation(summary = "手机用户列表")
    @GetMapping("/get-mobile-phone-user-list")
    public ResponseResult<List<MobilePhone>> getMobilePhoneUserList() {
        // 返回当前巡检人员的信息列表
        return ResponseResult.success(userService.getMobilePhoneUserList());
    }

    @Operation(summary = "删除事件")
    @GetMapping("/delete-event")
    public ResponseResult<Void> deleteEvent(@RequestParam @NotNull Integer id) {
        userService.deleteEventById(id);
        return ResponseResult.success();
    }

    @Operation(summary = "查看事件详细信息")
    @GetMapping("/get-incident-detail")
    public ResponseResult<IncidentDetailVO> getIncidentDetail(@RequestParam @NotNull Integer id) {
        return ResponseResult.success(userService.getIncidentDetail(id));
    }

    @Operation(summary = "处理反馈")
    @PostMapping("/to-feedback-incident")
    public ResponseResult<Void> toFeedbackIncident(@RequestBody @Valid ToFeedbackIncidentRequest toFeedbackIncidentRequest) {
        userService.toFeedbackIncident(toFeedbackIncidentRequest);
        return ResponseResult.success();
    }
}
