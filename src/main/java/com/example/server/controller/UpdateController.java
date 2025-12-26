package com.example.server.controller;

import com.example.server.model.request.IspPosUpdateRequest;
import com.example.server.model.vo.ResponseResult;
import com.example.server.service.impl.UpdateService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 接受后端发来的新坐标信息，通过WebSocket实时反馈给Web前端
 */
@Slf4j
@RestController
@RequestMapping("/internal/update")
public class UpdateController {
    @Autowired
    UpdateService updateService;
    @PostMapping("/isp-pos-update")
    public ResponseResult<Void> ispPosUpdate(@Valid @RequestBody IspPosUpdateRequest ispPosUpdateRequest) {
        updateService.ispPosUpdate(ispPosUpdateRequest);
        return ResponseResult.success();
    }
}
