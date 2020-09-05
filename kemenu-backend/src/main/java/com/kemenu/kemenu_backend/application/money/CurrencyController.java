package com.kemenu.kemenu_backend.application.money;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Locale;

@RestController
@AllArgsConstructor
@RequestMapping("/web/v1")
class CurrencyController {

    private final CurrencyService currencyService;

    @GetMapping("/currencies")
    ResponseEntity<List<CurrencyResponse>> allCurrencies(Locale locale) {
        return ResponseEntity.ok(currencyService.allCurrencies(locale));
    }
}
