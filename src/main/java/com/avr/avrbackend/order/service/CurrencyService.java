package com.avr.avrbackend.order.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CurrencyService {

    private static final String EXCHANGE_RATE_API_URL = "http://api.nbp.pl/api/exchangerates/rates/a/eur?format=json";

    public double getEuroExchangeRate() {
        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(EXCHANGE_RATE_API_URL, String.class);
        return parseEuroExchangeRate(response);
    }

    private double parseEuroExchangeRate(String response) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(response);
            return root.get("rates").get(0).get("mid").asDouble();
        } catch (Exception e) {
            e.printStackTrace();
            return 0.0;
        }
    }
}
