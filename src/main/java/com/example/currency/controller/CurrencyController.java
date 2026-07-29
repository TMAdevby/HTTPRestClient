package com.example.currency.controller;

import com.example.currency.service.CurrencyService;
import org.springframework.web.bind.annotation.*;

// @RestController = @Controller + @ResponseBody
// Говорит Spring: "Это контроллер, который возвращает данные (JSON), а не HTML страницы"
@RestController
@RequestMapping("/api/currency")  // Все методы этого контроллера начинаются с /api/currency
public class CurrencyController {

    private final CurrencyService currencyService;

    // Spring автоматически внедрит сюда объект CurrencyService
    public CurrencyController(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    /**
     * ЭТОТ МЕТОД ВЫЗЫВАЕТСЯ, КОГДА ФРОНТЕНД ДЕЛАЕТ ЗАПРОС
     * Пример: GET /api/currency/rate?currency=RUB
     */
    @GetMapping("/rate")
    public Double getRate(@RequestParam String currency) {
        // Вызываем сервис, который сходит на внешний API как КЛИЕНТ
        Double rate = currencyService.getExchangeRate(currency);

        // Возвращаем результат фронтенду
        // Spring автоматически превратит Double в JSON: 92.5
        return rate;
    }
}
