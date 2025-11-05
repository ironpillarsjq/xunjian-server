package com.example.server.controller;

import com.example.server.model.entity.MobilePhone;
import com.example.server.model.entity.SysUser;
import com.example.server.enums.AppExceptionCodeMsg;
import com.example.server.exception.AppException;
import com.example.server.model.request.*;
import com.example.server.security.context.UserContext;
import com.example.server.service.impl.AdminService;
import com.example.server.service.impl.AuthService;
import com.example.server.service.impl.SystemService;
import com.example.server.model.vo.ResponseResult;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Slf4j
@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AuthService authService;
    @Autowired
    private SystemService systemService;
    @Autowired
    private AdminService adminService;


    @Operation(summary = "编辑系统用户")
    @PostMapping("/edit-system-user")
    public ResponseResult<Void> editSystemUser(@Valid @RequestBody EditSystemUserRequest editSystemUserRequest) {
        systemService.curIsAdmin(); // 判断当前用户是否是admin，如果不是，则抛出异常
        adminService.editSystemUserByLoginID(editSystemUserRequest.getLoginID(), editSystemUserRequest.getUserName(), editSystemUserRequest.getUserDepartment(), editSystemUserRequest.getUserPhoneNum());
        return ResponseResult.success(null);
    }

    @Operation(summary = "编辑手机用户")
    @PostMapping("/edit-mobile-user")
    public ResponseResult<Void> editMobileUser(@Valid @RequestBody EditMobileUserRequest editMobileUserRequest) {
        systemService.curIsAdmin(); // 判断当前用户是否是admin，如果不是，则抛出异常
        adminService.editMobileUSerById(editMobileUserRequest);
        return ResponseResult.success(null);
    }

    @Value("${app.user.system-user-default-password}")
    private String systemUserDefaultPassword;

    @Operation(summary = "重置系统用户密码")
    @PostMapping("/reset-system-user-password")
    public ResponseResult<Void> resetSystemUserPassword(@Valid @RequestBody ResetSystemUserPasswordRequest resetSystemUserPasswordRequest) {
        systemService.curIsAdmin(); // 判断当前用户是否是admin，如果不是，则抛出异常
        adminService.resetSystemUserPassword(resetSystemUserPasswordRequest.getLoginID(), systemUserDefaultPassword);
        return ResponseResult.success(null);
    }

    @Operation(summary = "获得系统用户列表")
    @GetMapping("/get-system-user-list")
    public ResponseResult<List<SysUser>> getSystemUserList() {
        systemService.curIsAdmin(); // 判断当前用户是否是admin，如果不是，则抛出异常
        return ResponseResult.success(adminService.getSystemUserList());
    }

    @Operation(summary = "获得手机用户列表")
    @GetMapping("/get-mobile-user-list")
    public ResponseResult<List<MobilePhone>> getMobileUserList() {
        systemService.curIsAdmin(); // 判断当前用户是否是admin，如果不是，则抛出异常
        return ResponseResult.success(adminService.getMobileUserList());
    }

    @Operation(summary = "删除系统用户")
    @PostMapping("/delete-system-user")
    public ResponseResult<Void> deleteSystemUser(@Valid @RequestBody DeleteSystemUserRequest deleteSystemUserRequest) {
        systemService.curIsAdmin(); // 判断当前用户是否是admin，如果不是，则抛出异常
        if (Objects.equals(deleteSystemUserRequest.getLoginID(), UserContext.getUser().getLoginID())) {
            throw new AppException(AppExceptionCodeMsg.INVALID_DELETE);
        }
        System.out.println(UserContext.getUser().getLoginID());
        adminService.deleteSystemUser(deleteSystemUserRequest.getLoginID());
        return ResponseResult.success(null);
    }

    @Operation(summary = "删除手机用户")
    @GetMapping("/delete-mobile-user")
    public ResponseResult<Void> deleteMobileUser(@NotNull(message = "id不能为空") Integer id) {
        systemService.curIsAdmin();
        adminService.deleteMobileUser(id);
        return ResponseResult.success();
    }

    @Value("${app.user.system-user-default-password}")
    private String systemUserPassword;
    @Operation(summary = "添加系统用户")
    @PostMapping("/add-system-user")
    public ResponseResult<Void> addSystemUser(@Valid @RequestBody AddSystemUserRequest addSystemUserRequest) {
        systemService.curIsAdmin(); // 判断当前用户是否是admin，如果不是，则抛出异常
        adminService.addSysUser(addSystemUserRequest.getLoginID(), systemUserPassword, addSystemUserRequest.getUserName(), addSystemUserRequest.getIsAdmin(), addSystemUserRequest.getUserDepartment(), addSystemUserRequest.getUserPhoneNum());
        return ResponseResult.success(null);
    }

    @Operation(summary = "获得用户角色")
    @GetMapping("/get-user-roles")
    public ResponseResult<List<Map<String, Object>>> getUserRoles() {
        systemService.curIsAdmin(); // 判断当前用户是否是admin，如果不是，则抛出异常
        return ResponseResult.success(adminService.getRoles());
    }

    @Operation(summary = "添加系统用户")
    @PostMapping("/add-mobile-user")
    public ResponseResult<Void> addMobileUser(@Valid @RequestBody AddMobileUserRequest addMobileUserRequest) {
        systemService.curIsAdmin(); // 判断当前用户是否是admin，如果不是，则抛出异常
        adminService.addMobileUser(addMobileUserRequest);
        return ResponseResult.success();
    }

    @Operation(summary = "获取部门列表")
    @GetMapping("/get-departments")
    public ResponseResult<List<String>> getDepartments() {
        systemService.curIsAdmin(); // 判断当前用户是否是admin，如果不是，则抛出异常
        return ResponseResult.success(adminService.getDepartments());
    }

    @Operation(summary = "添加系统用户")
    @PostMapping("/to-handle-incident")
    public ResponseResult<Void> toHandleIncident(@Valid @RequestBody ToHandleIncidentRequest toHandleIncidentRequest) {
        adminService.toHandleIncident(toHandleIncidentRequest);
        return ResponseResult.success();
    }
}
