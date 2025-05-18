package com.CathayJavaTest.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import com.CathayJavaTest.persistence.client.CoinDeskResponse;
import com.CathayJavaTest.persistence.entity.Currency;
import com.CathayJavaTest.service.CurrencyService;
import com.CathayJavaTest.persistence.client.NewCoinDeskResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;


class CurrencyControllerTest {

    @Mock
    private CurrencyService currencyService;

    @InjectMocks
    private CurrencyController currencyController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // 初始化 Mock
    }



    @Test
    void testGetCurrency(){
        Currency mockCurrency = new Currency();
        mockCurrency.setId(1);
        mockCurrency.setCode("USD");
        mockCurrency.setChineseCode("美元");

        when(this.currencyService.getCurrencyByCode("USD")).thenReturn(mockCurrency);

        ResponseEntity<Currency> response = this.currencyController.getCurrency("USD");

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("USD", response.getBody().getCode());
        System.out.println("GET Method test : " + this.currencyService.getCurrencyByCode("USD"));
    }

    @Test
    void testAddCurrency() {
        Currency currency = new Currency();
        currency.setId(2);
        currency.setCode("EUR");
        currency.setChineseCode("歐元");

        when(this.currencyService.addCurrency(currency)).thenReturn(currency);

        ResponseEntity<Currency> response = this.currencyController.addCurrency(currency);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("EUR", response.getBody().getCode());
        System.out.println("POST Method test : " + this.currencyService.addCurrency(currency));
    }

    @Test
    void testUpdateCurrency() {

        Currency updateCurrency = new Currency();
        updateCurrency.setId(4);
        updateCurrency.setCode("NTD");
        updateCurrency.setChineseCode("新台幣");

        when(this.currencyService.updateCurrency(4, updateCurrency)).thenReturn(updateCurrency);

        ResponseEntity<Currency> response = this.currencyController.updateCurrency(4, updateCurrency);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("NTD", response.getBody().getCode());
        System.out.println("PUT Method test : " + this.currencyService.updateCurrency(4, updateCurrency));
    }

    @Test
    void testDeleteCurrency() {
        doNothing().when(this.currencyService).deleteCurrency(1);

        ResponseEntity<Void> response = this.currencyController.deleteCurrency(1);

        assertEquals(204, response.getStatusCodeValue());
        verify(this.currencyService, times(1)).deleteCurrency(1);
    }

    @Test
    void testGetCoinDesk() {
        CoinDeskResponse mockResponse = new CoinDeskResponse();

        CoinDeskResponse.Time time = new CoinDeskResponse.Time();
        time.setUpdated("Sep 2, 2024 07:07:20 UTC");
        time.setUpdatedISO("2024-09-02T07:07:20+00:00");
        time.setUpdatedUk("Sep 2, 2024 at 08:07 BST");
        mockResponse.setTime(time);

        mockResponse.setDisclaimer("just for test");
        mockResponse.setChartName("Bitcoin");

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

        CoinDeskResponse.CurrencyInfo eurCurrencyInfo = new CoinDeskResponse.CurrencyInfo();
        eurCurrencyInfo.setCode("EUR");
        eurCurrencyInfo.setSymbol("&euro;");
        eurCurrencyInfo.setRate("52,243.287");
        eurCurrencyInfo.setDescription("Euro");
        eurCurrencyInfo.setRateFloat(52243.2865);

        bpi.put("USD",usdCurrencyInfo);
        bpi.put("GBP",gbpCurrencyInfo);
        bpi.put("EUR",eurCurrencyInfo);
        mockResponse.setBpi(bpi);

        when(this.currencyService.getCoinDeskContent()).thenReturn(mockResponse);

        ResponseEntity<CoinDeskResponse> response = this.currencyController.getCoinDesk();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Bitcoin", response.getBody().getChartName());
        assertEquals("EUR", response.getBody().getBpi().get("EUR").getCode());

        System.out.println("Test - GetCoinDesk : " + response.getBody());
    }

    @Test
    void testGetNewCoinDesk() {
        NewCoinDeskResponse mockResponse = new NewCoinDeskResponse();
        mockResponse.setUpdateTime("Sep 2, 2024 07:07:20 UTC");

        NewCoinDeskResponse.NewCurrencyInfo usdNewCurrencyInfo = new NewCoinDeskResponse.NewCurrencyInfo("USD", "美元", "57,756.298");
        NewCoinDeskResponse.NewCurrencyInfo gbpNewCurrencyInfo = new NewCoinDeskResponse.NewCurrencyInfo("GBP", "英鎊", "43,984.02");
        NewCoinDeskResponse.NewCurrencyInfo eurNewCurrencyInfo = new NewCoinDeskResponse.NewCurrencyInfo("EUR", "歐元", "52,243.287");

        mockResponse.getBpi().put("USD", usdNewCurrencyInfo);
        mockResponse.getBpi().put("GBP", gbpNewCurrencyInfo);
        mockResponse.getBpi().put("EUR", eurNewCurrencyInfo);

        when(this.currencyService.getCoinDeskModifiedContent()).thenReturn(mockResponse);

        ResponseEntity<NewCoinDeskResponse> response = this.currencyController.getNewCoinDesk();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Sep 2, 2024 07:07:20 UTC", response.getBody().getUpdateTime());
        assertEquals("美元", response.getBody().getBpi().get("USD").getChineseCode());
        assertEquals("52,243.287", response.getBody().getBpi().get("EUR").getRate());

        System.out.println("Test - GetNewCoinDesk : " + response.getBody());
    }


}