package com.CathayJavaTest.controller;

import com.CathayJavaTest.persistence.client.CoinDeskResponse;
import com.CathayJavaTest.persistence.entity.Currency;
import com.CathayJavaTest.service.CurrencyService;
import com.CathayJavaTest.service.NewCoinDeskResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/currency")
public class CurrencyController {

    private final CurrencyService currencyService;

    @Autowired
    public CurrencyController(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    @GetMapping("/{code}")
    public ResponseEntity<Currency> getCurrency(@PathVariable String code) {
        return ResponseEntity.ok(this.currencyService.getCurrencyByCode(code));
    }

    @PostMapping
    public ResponseEntity<Currency> addCurrency(@RequestBody Currency currency) {
        return ResponseEntity.ok(this.currencyService.addCurrency(currency));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Currency> updateCurrency(@PathVariable Integer id, @RequestBody Currency currency) {
        return ResponseEntity.ok(this.currencyService.updateCurrency(id, currency));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCurrency(@PathVariable Integer id) {
        this.currencyService.deleteCurrency(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/coindesk")
    public ResponseEntity<CoinDeskResponse> getCoinDesk() {
        return ResponseEntity.ok(this.currencyService.getCoinDeskContent());
    }

    @GetMapping("/newcoindesk")
    public ResponseEntity<NewCoinDeskResponse> getNewCoinDesk() {
        return ResponseEntity.ok(this.currencyService.getCoinDeskModifiedContent());
    }
}
