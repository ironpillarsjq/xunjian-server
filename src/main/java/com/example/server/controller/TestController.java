package com.example.server.controller;


import com.example.server.mapper.TestMapper;
import com.example.server.model.entity.Test;
import com.example.server.model.request.EditSystemUserRequest;
import com.example.server.model.request.TestTimeRequest;
import com.example.server.model.vo.ResponseResult;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private TestMapper testMapper;

    @Operation(summary = "测试时间格式问题")
    @PostMapping("/time-insert")
    public ResponseResult<Void> testInsert(@Valid @RequestBody TestTimeRequest testTimeRequest) {

        Test test = new Test();
        test.setTestTime(testTimeRequest.getTestTime());
        LoggerFactory.getLogger(getClass()).info("进入testInsert");

        testMapper.insert(test);
        return ResponseResult.success(null);
    }

    @Operation(summary = "测试时间格式问题")
    @GetMapping("/time-get")
    public ResponseResult<Test> testGet() {
        LoggerFactory.getLogger(getClass()).info("进入time-get");
        return ResponseResult.success(testMapper.selectOne(null));
    }

}
