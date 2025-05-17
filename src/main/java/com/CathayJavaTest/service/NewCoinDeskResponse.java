package com.CathayJavaTest.service;

import java.util.HashMap;
import java.util.Map;

public class NewCoinDeskResponse {

    private String updateTime;

    private Map<String, NewCurrencyInfo> bpi = new HashMap<>();

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public Map<String, NewCurrencyInfo> getBpi() {
        return bpi;
    }

    public static class NewCurrencyInfo {
        private String code;
        private String chineseCode;
        private String rate;

        public NewCurrencyInfo(String code, String chineseCode, String rate) {
            this.code = code;
            this.chineseCode = chineseCode;
            this.rate = rate;
        }

        public String getCode() { return code; }
        public void setCode(String code) { this.code = code; }

        public String getChineseCode() { return chineseCode; }

        public void setChineseCode(String chineseCode) { this.chineseCode = chineseCode; }

        public String getRate() { return rate; }
        public void setRate(String rate) { this.rate = rate; }

    }
}
