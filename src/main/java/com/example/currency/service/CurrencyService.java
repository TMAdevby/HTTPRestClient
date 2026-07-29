package com.example.currency.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import java.util.Map;

@Service
public class CurrencyService {

    private final RestClient restClient;

    @Value("${external.api.currency-url}")
    private String currencyApiUrl;

    public CurrencyService(RestClient.Builder restClientBuilder) {
        this.restClient = restClientBuilder
                .baseUrl(currencyApiUrl)
                .build();
    }

    /**
     * Получает курс валюты относительно USD
     */
    public Double getExchangeRate(String currencyCode) {
        // ВАЖНО: Всегда запрашиваем /USD (базовая валюта)
        // API вернет курсы ВСЕХ валют относительно USD
        Map<String, Object> response = restClient.get()
                .uri("/USD")  // <-- ИСПРАВЛЕНО: всегда запрашиваем USD
                .retrieve()
                .body(Map.class);

        // Логируем, что пришло от API (для отладки)
        System.out.println("Ответ от внешнего API: " + response);

        // Достаем карту курсов
        Map<String, Double> rates = (Map<String, Double>) response.get("rates");

        // Логируем карту курсов
        System.out.println("Курсы валют: " + rates);

        // Возвращаем курс для запрошенной валюты
        Double rate = rates.get(currencyCode.toUpperCase());

        // Логируем итоговое значение
        System.out.println("Курс для " + currencyCode + ": " + rate);

        return rate;
    }
}