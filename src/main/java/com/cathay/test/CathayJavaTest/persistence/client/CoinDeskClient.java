package com.cathay.test.CathayJavaTest.persistence.client;


import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;

@Component
public class CoinDeskClient {

    private final RestTemplate restTemplate;
    private static final String URL = "https://kengp3.github.io/blog/coindesk.json";

    public CoinDeskClient() {
        this.restTemplate = new RestTemplate();
    }

    public CoinDeskResponse getCoinDeskData() {
        ResponseEntity<CoinDeskResponse> response = restTemplate.getForEntity(URL, CoinDeskResponse.class);
        return response.getBody();
    }
}
