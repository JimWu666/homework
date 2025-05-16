package com.cathay.test.CathayJavaTest.service;

import com.cathay.test.CathayJavaTest.entity.Currency;
import com.cathay.test.CathayJavaTest.handler.NotFoundException;
import com.cathay.test.CathayJavaTest.repository.CurrencyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CurrencyService {

    private CurrencyRepository currencyRepository;

    @Autowired
    public CurrencyService(CurrencyRepository currencyRepository) {
        this.currencyRepository = currencyRepository;
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

}
