package com.kemenu.kemenu_backend.application.money;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Currency;
import java.util.List;
import java.util.Locale;

import static java.util.stream.Collectors.toList;

@RestController
@AllArgsConstructor
@RequestMapping("/web/v1")
class CurrencyController {

    private final MoneyFormatter moneyFormatter;

    @GetMapping("/currencies")
    ResponseEntity<List<CurrencyResponse>> allCurrencies(Locale locale) {
        return ResponseEntity.ok(
                Currency.getAvailableCurrencies().stream()
                        .map(c -> CurrencyResponse.builder()
                                .isoCode(c.getCurrencyCode())
                                .name(c.getDisplayName(locale))
                                .symbol(moneyFormatter.getSymbol(c.getCurrencyCode()))
                                .build()
                        )
                        .collect(toList())
        );
    }
}
