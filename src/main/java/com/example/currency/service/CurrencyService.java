package com.example.currency.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import java.util.Map;

// Аннотация @Service говорит Spring: "Это бизнес-логика, создай объект этого класса"
@Service
public class CurrencyService {

    // RestClient - это наш HTTP-КЛИЕНТ для обращения к внешним API
    private final RestClient restClient;

    // Берем значение из application.yml (external.api.currency-url)
    @Value("${external.api.currency-url}")
    private String currencyApiUrl;

    // Конструктор вызывается Spring при создании бина
    // Spring автоматически создаст RestClient через @Bean (см. конфигурацию ниже)
    public CurrencyService(RestClient.Builder restClientBuilder) {
        // Создаем RestClient с базовым URL из конфига
        this.restClient = restClientBuilder
                .baseUrl(currencyApiUrl)  // Базовый URL: https://api.exchangerate-api.com/v4/latest
                .build();
    }

    /**
     * Метод получает курс валюты от ВНЕШНЕГО API
     * Здесь наш Spring ВЫСТУПАЕТ В РОЛИ КЛИЕНТА
     */
    public Double getExchangeRate(String currencyCode) {
        // Формируем полный URL: https://api.exchangerate-api.com/v4/latest/USD

        // Делаем HTTP GET запрос к внешнему серверу
        Map<String, Object> response = restClient.get()
                .uri("/{currency}", currencyCode)  // Подставляем currencyCode в URL
                .retrieve()  // Отправляем запрос и ждем ответ
                .body(Map.class);  // Превращаем JSON ответ в Map

        // Ответ от API выглядит так:
        // {
        //   "base": "USD",
        //   "rates": {
        //     "RUB": 92.5,
        //     "EUR": 0.95,
        //     ...
        //   }
        // }

        // Достаем карту курсов из ответа
        Map<String, Double> rates = (Map<String, Double>) response.get("rates");

        // Возвращаем курс для запрошенной валюты
        return rates.get(currencyCode.toUpperCase());
    }
}