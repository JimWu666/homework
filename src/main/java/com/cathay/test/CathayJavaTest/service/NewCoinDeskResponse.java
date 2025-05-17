package com.cathay.test.CathayJavaTest.service;

import com.cathay.test.CathayJavaTest.persistence.client.CoinDeskResponse;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

public class NewCoinDeskResponse {

    private String updateTime;

    private Map<String, CurrencyInfo> bpi;

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }
    public String getUpdateTime() {
        return updateTime;
    }

    public Map<String, CurrencyInfo> getBpi() {
        return bpi;
    }

    public static class CurrencyInfo {
        private String code;
        private String chineseCode;
        private String rate;

        public String getCode() { return code; }
        public void setCode(String code) { this.code = code; }

        public String getChineseCode() { return chineseCode; }

        public void setChineseCode(String chineseCode) { this.chineseCode = chineseCode; }

        public String getRate() { return rate; }
        public void setRate(String rate) { this.rate = rate; }

    }
}
