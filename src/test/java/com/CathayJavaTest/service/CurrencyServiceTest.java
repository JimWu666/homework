package com.CathayJavaTest.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.CathayJavaTest.persistence.client.CoinDeskClient;
import com.CathayJavaTest.persistence.client.CoinDeskResponse;
import com.CathayJavaTest.persistence.client.NewCoinDeskResponse;
import com.CathayJavaTest.persistence.entity.Currency;
import com.CathayJavaTest.persistence.repository.CurrencyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

class CurrencyServiceTest {

    @Mock
    private CoinDeskClient coinDeskClient;

    @Mock
    private CurrencyRepository currencyRepository;

    @InjectMocks
    private CurrencyService currencyService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetCoinDeskModifiedContent() {
        // 模擬 CoinDesk API 回應
        CoinDeskResponse mockResponse = new CoinDeskResponse();

        // Set CoinDeskResponse Time
        CoinDeskResponse.Time time = new CoinDeskResponse.Time();
        time.setUpdated("Sep 2, 2024 07:07:20 UTC");
        time.setUpdatedISO("2024-09-02T07:07:20+00:00");
        time.setUpdatedUk("Sep 2, 2024 at 08:07 BST");
        mockResponse.setTime(time);

        // Set CoinDeskResponse Disclaimer & ChartName
        mockResponse.setDisclaimer("just for test");
        mockResponse.setChartName("Bitcoin");

        // Set CoinDeskResponse bpi
        Map<String, CoinDeskResponse.CurrencyInfo> bpi = new HashMap<>();

        CoinDeskResponse.CurrencyInfo usdCurrencyInfo = new CoinDeskResponse.CurrencyInfo();
        usdCurrencyInfo.setCode("USD");
        usdCurrencyInfo.setSymbol("&#36;");
        usdCurrencyInfo.setRate("57,756.298");
        usdCurrencyInfo.setDescription("United States Dollar");
        usdCurrencyInfo.setRateFloat(57756.2984);

        CoinDeskResponse.CurrencyInfo gbpCurrencyInfo = new CoinDeskResponse.CurrencyInfo();
        gbpCurrencyInfo.setCode("GBP");
        gbpCurrencyInfo.setSymbol("&pound;");
        gbpCurrencyInfo.setRate("43,984.02");
        gbpCurrencyInfo.setDescription("British Pound Sterling");
        gbpCurrencyInfo.setRateFloat(43984.0203);

        CoinDeskResponse.CurrencyInfo ntdCurrencyInfo = new CoinDeskResponse.CurrencyInfo();
        ntdCurrencyInfo.setCode("NTD");
        ntdCurrencyInfo.setSymbol("&pound;");
        ntdCurrencyInfo.setRate("43,984.02");
        ntdCurrencyInfo.setDescription("New Taiwan Dollar");
        ntdCurrencyInfo.setRateFloat(43984.0203);

        bpi.put("USD",usdCurrencyInfo);
        bpi.put("GBP",gbpCurrencyInfo);
        bpi.put("NTD",ntdCurrencyInfo);
        mockResponse.setBpi(bpi);


        when(this.coinDeskClient.getCoinDeskData()).thenReturn(mockResponse);

        // 模擬幣別對應表（資料庫）
        Currency usdCurrency = new Currency();
        usdCurrency.setId(1);
        usdCurrency.setCode("USD");
        usdCurrency.setChineseCode("美元");

        Currency gbpCurrency = new Currency();
        gbpCurrency.setId(2);
        gbpCurrency.setCode("GBP");
        gbpCurrency.setChineseCode("英鎊");


        when(this.currencyRepository.findByCode("USD")).thenReturn(Optional.of(usdCurrency));
        when(this.currencyRepository.findByCode("GBP")).thenReturn(Optional.of(gbpCurrency));
        when(this.currencyRepository.findByCode("NTD")).thenReturn(Optional.empty()); // GBP 不存在於資料庫

        // 執行方法
        NewCoinDeskResponse newResponse = this.currencyService.getCoinDeskModifiedContent();

        // 驗證結果
        assertEquals("2024/09/02 07:07:20", newResponse.getUpdateTime()); // 確保時間格式轉換成功
        assertEquals("美元", newResponse.getBpi().get("USD").getChineseCode());
        assertEquals("", newResponse.getBpi().get("NTD").getChineseCode());
        assertEquals("43,984.02", newResponse.getBpi().get("NTD").getRate());
        assertEquals("英鎊", newResponse.getBpi().get("GBP").getChineseCode());

        System.out.println(newResponse);
    }
}