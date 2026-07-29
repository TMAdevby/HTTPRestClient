package com.example.currency.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class CurrencyService {

    private final RestClient restClient;

    public CurrencyService(RestClient.Builder restClientBuilder) {
        // Базовый URL внешнего API
        this.restClient = restClientBuilder
                .baseUrl("https://api.exchangerate-api.com/v4/latest")
                .build();
    }

    /**
     * Получает курс валюты относительно USD
     */
    public Double getExchangeRate(String currencyCode) {
        System.out.println("=== ЗАПРОС К ВНЕШНЕМУ API ===");
        System.out.println("Запрашиваем курс для: " + currencyCode);

        // Делаем запрос и получаем ОТВЕТ КАК СТРОКУ (чтобы увидеть сырой JSON)
        String jsonResponse = restClient.get()
                .uri("/USD")  // Всегда запрашиваем базовую валюту USD
                .retrieve()
                .body(String.class);  // <-- Получаем как String, не как Map!

        System.out.println("Сырой ответ от API:");
        System.out.println(jsonResponse);
        System.out.println("================================\n");

        // Простой парсинг: ищем строку "RUB":92.5
        // В реальном проекте используйте Jackson ObjectMapper
        String searchKey = "\"" + currencyCode.toUpperCase() + "\":";
        int startIndex = jsonResponse.indexOf(searchKey);

        if (startIndex == -1) {
            System.err.println("Валюта " + currencyCode + " не найдена в ответе!");
            return 0.0;
        }

        // Извлекаем число после ключа
        startIndex += searchKey.length();
        int endIndex = jsonResponse.indexOf(",", startIndex);
        if (endIndex == -1) {
            endIndex = jsonResponse.indexOf("}", startIndex);
        }

        String rateStr = jsonResponse.substring(startIndex, endIndex).trim();
        Double rate = Double.parseDouble(rateStr);

        System.out.println("Извлеченный курс для " + currencyCode + ": " + rate);

        return rate;
    }
}