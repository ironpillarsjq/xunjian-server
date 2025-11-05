package com.example.server;


import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class Test {
    @org.junit.jupiter.api.Test
    public void test() {
        // 获取当前的带时区的日期时间
        ZonedDateTime zonedDateTime = ZonedDateTime.now();
        System.out.println("Current ZonedDateTime: " + zonedDateTime);

        // 获取带纳秒级别精度的 ZonedDateTime
        ZonedDateTime highPrecisionTime = ZonedDateTime.of(2025, 10, 31, 13, 45, 30, 123456789, zonedDateTime.getZone());
        System.out.println("High Precision ZonedDateTime: " + highPrecisionTime);

        // 格式化输出时间（纳秒会被格式化显示）
        String formattedTime = highPrecisionTime.format(DateTimeFormatter.ISO_ZONED_DATE_TIME);
        System.out.println("Formatted High Precision Time: " + formattedTime);

    }
}
