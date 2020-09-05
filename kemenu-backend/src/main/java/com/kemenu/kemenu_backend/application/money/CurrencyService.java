package com.kemenu.kemenu_backend.application.money;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Currency;
import java.util.List;
import java.util.Locale;

import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
public class CurrencyService {

    private final MoneyFormatter moneyFormatter;

    public List<CurrencyResponse> allCurrencies(Locale locale) {
        return Currency.getAvailableCurrencies().stream()
                .map(c -> CurrencyResponse.builder()
                        .isoCode(c.getCurrencyCode())
                        .name(c.getDisplayName(locale))
                        .symbol(moneyFormatter.getSymbol(c.getCurrencyCode()))
                        .build()
                )
                .collect(toList());
    }
}
