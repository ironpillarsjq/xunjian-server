package com.example.server.controller;

import com.example.server.service.impl.UpdateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/update")
public class UpdateController {
    UpdateService updateService;
    @PostMapping("/isp-pos-update")
    public void ispPosUpdate() {
        updateService.rcvIspPosUpdate();
    }
}
