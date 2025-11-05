package com.example.server.service.impl;

import com.example.server.mapper.EventMapper;
import com.example.server.model.entity.Event;
import com.example.server.model.entity.MobilePhone;
import com.example.server.model.entity.SysUser;
import com.example.server.enums.AppExceptionCodeMsg;
import com.example.server.exception.AppException;
import com.example.server.mapper.MobilePhoneMapper;
import com.example.server.mapper.SysUserMapper;
import com.example.server.model.request.AddMobileUserRequest;
import com.example.server.model.request.EditMobileUserRequest;
import com.example.server.model.request.ToHandleIncidentRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AdminService {
    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private MobilePhoneMapper mobilePhoneMapper;


    private final String ROLE_ADMIN = "系统管理员";
    private final String ROLE_USER = "普通用户";
    @Autowired
    private EventMapper eventMapper;

    public void addSysUser(String loginID, String loginPwd, String userName, Boolean isAdmin, String userDepartment, String userPhoneNum) {
        if (sysUserMapper.existsByLoginID(loginID)) {
            // 用户已注册
            throw new AppException(AppExceptionCodeMsg.USER_ALREADY_REGISTERED);
        }
        sysUserMapper.addSysUser(loginID, loginPwd, userName, isAdmin ? ROLE_ADMIN : ROLE_USER, userDepartment, userPhoneNum);
    }

    public void editSystemUserByLoginID(String loginID, String userName, String userDepartment, String userPhoneNum) {
        sysUserMapper.editSystemUserByLoginID(loginID, userName, userDepartment, userPhoneNum);
    }

    public void resetSystemUserPassword(String loginID, String systemUserDefaultPassword) {
        sysUserMapper.setPasswordByLoginID(loginID, systemUserDefaultPassword);
    }

    public List<SysUser> getSystemUserList() {
        return sysUserMapper.getSysUserList();
    }

    public void deleteSystemUser(String loginID) {
        sysUserMapper.deleteSystemUserByLoginID(loginID);
    }

    public List<Map<String, Object>> getRoles() {
        Map<String, Object> roleAdmin = new HashMap<>();
        Map<String, Object> roleUser = new HashMap<>();
        roleAdmin.put("isAdmin", true);
        roleAdmin.put("roleName", ROLE_ADMIN);
        roleUser.put("isAdmin", false);
        roleUser.put("roleName", ROLE_USER);
        List<Map<String, Object>> roles = new ArrayList<>();
        roles.add(roleAdmin);
        roles.add(roleUser);
        return roles;
    }

    public void deleteMobileUser(@NotNull(message = "id不能为空") Integer id) {
        if (mobilePhoneMapper.countById(id) == 0) {
            throw new AppException(AppExceptionCodeMsg.USER_NOT_FOUND);
        }
        mobilePhoneMapper.deleteById(id);
    }

    public void addMobileUser(@Valid AddMobileUserRequest addMobileUserRequest) {
        // 判断用户是否已经存在
        if (mobilePhoneMapper.countByImei(addMobileUserRequest.getImei()) > 0) {
            throw new AppException(AppExceptionCodeMsg.USER_ALREADY_REGISTERED);
        }

        MobilePhone mobilePhone = new MobilePhone();
        mobilePhone.setPhoneNum(addMobileUserRequest.getPhoneNum());
        mobilePhone.setPhoneUserName(addMobileUserRequest.getPhoneUserName());
        mobilePhone.setImei(addMobileUserRequest.getImei());
        mobilePhone.setAddress(addMobileUserRequest.getAddress());

        mobilePhoneMapper.insert(mobilePhone);
    }

    public List<MobilePhone> getMobileUserList() {
        return mobilePhoneMapper.selectList(null);
    }

    public void editMobileUSerById(@Valid EditMobileUserRequest editMobileUserRequest) {
        // 先判断用户是否存在
        if (mobilePhoneMapper.countById(editMobileUserRequest.getId()) == 0) {
            throw new AppException(AppExceptionCodeMsg.USER_NOT_FOUND);
        }

        // IMEI不能重复
        if (mobilePhoneMapper.countByImeiAndIdNotIn(editMobileUserRequest.getImei(), Arrays.asList(editMobileUserRequest.getId())) > 0) {
            throw new AppException(AppExceptionCodeMsg.IMEI_ALREADY_EXITS);
        }


        MobilePhone mobilePhone = new MobilePhone();
        mobilePhone.setId(editMobileUserRequest.getId());
        mobilePhone.setPhoneNum(editMobileUserRequest.getPhoneNum());
        mobilePhone.setPhoneUserName(editMobileUserRequest.getPhoneUserName());
        mobilePhone.setImei(editMobileUserRequest.getImei());
        mobilePhone.setAddress(editMobileUserRequest.getAddress());

        mobilePhoneMapper.updateById(mobilePhone);
    }


    public List<String> getDepartments() {
        return sysUserMapper.selectUserDepartments();
    }

    public void toHandleIncident(@Valid ToHandleIncidentRequest toHandleIncidentRequest) {
        Event event = new Event();
        event.setId(toHandleIncidentRequest.getId());
        event.setChuLiType(toHandleIncidentRequest.getChuLiType());
        event.setChengBanDanWei(toHandleIncidentRequest.getChengBanDanWei());
        event.setBanLIYaoQiu(toHandleIncidentRequest.getBanLiYaoQiu());
        event.setIfBanJie("待反馈");
        eventMapper.updateById(event);
    }
}
