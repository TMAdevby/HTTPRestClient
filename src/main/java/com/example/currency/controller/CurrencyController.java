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
    public Double getRate(@RequestParam String currency) {
        System.out.println("=== ПОЛУЧЕН ЗАПРОС ОТ ФРОНТЕНДА ===");
        System.out.println("Запрошенная валюта: " + currency);

        Double rate = currencyService.getExchangeRate(currency);

        System.out.println("Возвращаем фронтенду значение: " + rate);
        System.out.println("Тип возвращаемого значения: " + (rate != null ? rate.getClass().getName() : "null"));
        System.out.println("=====================================\n");

        return rate != null ? rate : 0.0;
    }
}