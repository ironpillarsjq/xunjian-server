package com.example.server;

import com.example.server.mapper.EventMapper;
import com.example.server.model.entity.Event;
import com.example.server.service.impl.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Timestamp;
import java.util.List;

@SpringBootTest
public class TestDemo {

    @Autowired
    EventMapper eventMapper;


    @Test
    public void test() {

    }
}
