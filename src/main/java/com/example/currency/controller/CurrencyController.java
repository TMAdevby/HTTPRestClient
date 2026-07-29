package com.example.currency.controller;

import com.example.currency.service.CurrencyService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/currency")
public class CurrencyController {

    private final CurrencyService currencyService;

    public CurrencyController(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    @GetMapping("/rate")
    public double getRate(@RequestParam String currency) {
        // Вызываем сервис
        Double rate = currencyService.getExchangeRate(currency);

        // Если курс не найден, возвращаем 0.0
        if (rate == null) {
            return 0.0;
        }

        // Явно приводим к double (примитивный тип)
        // Spring превратит это в JSON число: 92.5
        return rate.doubleValue();
    }
}