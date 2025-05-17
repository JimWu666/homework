package com.CathayJavaTest.persistence.client;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;

public class CoinDeskResponse {

    private Time time;
    private String disclaimer;
    private String chartName;
    private Map<String, CurrencyInfo> bpi;

    public Time getTime() { return time; }
    public void setTime(Time time) { this.time = time; }

    public String getDisclaimer() { return disclaimer; }
    public void setDisclaimer(String disclaimer) { this.disclaimer = disclaimer; }

    public String getChartName() { return chartName; }
    public void setChartName(String chartName) { this.chartName = chartName; }

    public Map<String, CurrencyInfo> getBpi() { return bpi; }
    public void setBpi(Map<String, CurrencyInfo> bpi) { this.bpi = bpi; }

    public static class Time {
        private String updated;
        private String updatedISO;
        @JsonProperty("updateduk")
        private String updatedUk;

        public String getUpdated() { return updated; }
        public void setUpdated(String updated) { this.updated = updated; }

        public String getUpdatedISO() { return updatedISO; }
        public void setUpdatedISO(String updatedISO) { this.updatedISO = updatedISO; }

        public String getUpdatedUk() { return updatedUk; }
        public void setUpdatedUk(String updatedUk) { this.updatedUk = updatedUk; }
    }

    public static class CurrencyInfo {
        private String code;
        private String symbol;
        private String rate;
        private String description;
        private double rateFloat;

        public String getCode() { return code; }
        public void setCode(String code) { this.code = code; }

        public String getSymbol() { return symbol; }
        public void setSymbol(String symbol) { this.symbol = symbol; }

        public String getRate() { return rate; }
        public void setRate(String rate) { this.rate = rate; }

        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }

        public double getRateFloat() { return rateFloat; }
        public void setRateFloat(double rateFloat) { this.rateFloat = rateFloat; }
    }
}