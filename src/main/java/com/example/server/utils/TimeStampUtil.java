package com.example.server.utils;

import java.sql.Timestamp;

public class TimeStampUtil {
    public static Timestamp getTimeStamp() {
        return new Timestamp(System.currentTimeMillis());
    }
}
