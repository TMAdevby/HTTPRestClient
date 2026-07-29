package com.example.currency;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// Эта аннотация говорит Spring: "Запусти всё, что нужно для веб-приложения"
// Она автоматически находит все @RestController, @Service, @Configuration и создает их
@SpringBootApplication
public class CurrencyApplication {

    public static void main(String[] args) {
        // Запускаем Spring Boot приложение
        // Spring создаст встроенный Tomcat, подключит все бины и начнет слушать порт 8080
        SpringApplication.run(CurrencyApplication.class, args);
    }
}