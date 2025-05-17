package com.CathayJavaTest.service;

import com.CathayJavaTest.service.handler.NotFoundException;
import com.CathayJavaTest.persistence.client.CoinDeskClient;
import com.CathayJavaTest.persistence.client.CoinDeskResponse;
import com.CathayJavaTest.persistence.entity.Currency;
import com.CathayJavaTest.persistence.repository.CurrencyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

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
        return this.currencyRepository.findByCode(code).orElseThrow(() -> new NotFoundException("Currency Code Not Found"));
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

        // get CoinDeskClient API
        CoinDeskResponse response = this.coinDeskClient.getCoinDeskData();

        // Init NewCoinDeskResponse
        NewCoinDeskResponse newCoinDeskResponse = new NewCoinDeskResponse();

        // Get CoinDeskClient and change format to yyyy/mm/dd hh:mm:ss z
        String newDateFormat = changeDateFormat(response.getTime().getUpdated());

        // Set Update Time To NewCoinDeskResponse
        newCoinDeskResponse.setUpdateTime(newDateFormat);

        // Get CoinDeskClient Bpi Currency code
        List<String> codes = new ArrayList<>(response.getBpi().keySet());
        // codes ["USD", "GBP", "TWD"] -> repository IN codes (select * from currency where code IN []) -> List<Currency> -> Map<String (UDS, TWD..), Currency>


        // Set NewCoinDeskResponse Currency Info
        Map<String, String> codeToChineseCode = new HashMap<>();
        for (String code : codes) {

            // Get Currency code and go Currency Table find correspond chineseCode
            Optional<Currency> optionalCurrency = this.currencyRepository.findByCode(code);

            // If code not in DataTable then set chineseCode empty
            if (optionalCurrency.isPresent()) {
                codeToChineseCode.put(code,optionalCurrency.get().getChineseCode());
            }else {
                codeToChineseCode.put(code, "");
            }
        }

        // Set CoinDeskResponse CurrencyInfo
        for (Map.Entry<String, CoinDeskResponse.CurrencyInfo> entry : response.getBpi().entrySet()) {
            newCoinDeskResponse.getBpi().put(entry.getKey(), new NewCoinDeskResponse.NewCurrencyInfo(
                    entry.getValue().getCode(), codeToChineseCode.get(entry.getValue().getCode()) ,entry.getValue().getRate()));
        }

        return newCoinDeskResponse;
    }

    private String changeDateFormat(String dateFormat) {

        // 原始時間字串
        String inputTime = "Sep 2, 2024 07:07:20 UTC";

        // 定義解析器（使用 Locale.ENGLISH 確保英文月份解析）
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("MMM d, yyyy HH:mm:ss z", Locale.ENGLISH);

        // 解析時間字串
        ZonedDateTime zonedDateTime = ZonedDateTime.parse(inputTime, inputFormatter);

        // 轉換為指定格式
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

        return zonedDateTime.format(outputFormatter);
    }
}
