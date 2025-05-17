package com.cathay.test.CathayJavaTest.service;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

class CurrencyServiceTest {

    @Test
    void getCurrencyByCodeSuccessfully() {
    }

    @Test
    void getCurrencyByCodeButNotFound() {
    }


    @Test
    void addCurrency() {
    }

    @Test
    void updateCurrency() {
    }

    @Test
    void deleteCurrency() {
    }

    @Test
    void timeChangeTest() {
        // 原始時間字串
        String inputTime = "Sep 2, 2024 07:07:20 UTC";

        // 定義解析器
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("MMM d, yyyy HH:mm:ss z");
        ZonedDateTime zonedDateTime = ZonedDateTime.parse(inputTime, inputFormatter);
        // 轉換為目標時區（UTC 或其他時區）
        LocalDateTime targetTime = zonedDateTime.withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime();
        // 定義輸出格式
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        String formattedTime = targetTime.format(outputFormatter);

        System.out.println(formattedTime);
    }
}