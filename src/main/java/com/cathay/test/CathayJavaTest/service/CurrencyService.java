package com.cathay.test.CathayJavaTest.service;

import com.cathay.test.CathayJavaTest.persistence.client.CoinDeskClient;
import com.cathay.test.CathayJavaTest.persistence.client.CoinDeskResponse;
import com.cathay.test.CathayJavaTest.persistence.entity.Currency;
import com.cathay.test.CathayJavaTest.service.handler.NotFoundException;
import com.cathay.test.CathayJavaTest.persistence.repository.CurrencyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class CurrencyService {

    private final CurrencyRepository currencyRepository;
    private final CoinDeskClient coinDeskClient;

    @Autowired
    public CurrencyService(CurrencyRepository currencyRepository, CoinDeskClient coinDeskClient) {
        this.currencyRepository = currencyRepository;
        this.coinDeskClient = coinDeskClient;
    }

    public Currency getCurrencyByCode(String code) {
        return this.currencyRepository.findByCode(code).orElseThrow(() -> new NotFoundException("Currency Not Found"));
    }

    public Currency addCurrency(Currency currency) {
        return this.currencyRepository.save(currency);
    }

    public Currency updateCurrency(Integer id, Currency updateCurrency) {
        return this.currencyRepository.findById(id).map(currency -> {
            currency.setCode(updateCurrency.getCode());
            currency.setChineseCode(updateCurrency.getChineseCode());
            return this.currencyRepository.save(currency);
        }).orElseThrow(() -> new NotFoundException("Currency Not Found!!"));
    }

    public void deleteCurrency(Integer id) {
        this.currencyRepository.deleteById(id);
    }

    public CoinDeskResponse getCoinDeskContent() {
        return this.coinDeskClient.getCoinDeskData();
    }

    public NewCoinDeskResponse getCoinDeskModifiedContent() {

        CoinDeskResponse response = this.coinDeskClient.getCoinDeskData();

        NewCoinDeskResponse coinDeskResponse = new NewCoinDeskResponse();

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



        coinDeskResponse.setUpdateTime(response.getTime().getUpdated());

        return null;
    }
}
