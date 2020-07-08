package com.kemenu.kemenu_backend.application.money;

import lombok.Getter;
import org.javamoney.moneta.Money;
import org.springframework.stereotype.Component;

import javax.money.CurrencyUnit;
import javax.money.format.MonetaryFormats;
import java.math.BigDecimal;
import java.util.Currency;
import java.util.Locale;
import java.util.Map;

import static java.util.Objects.isNull;

@Getter
@Component
public class MoneyFormatter {

    private final Map<String, String> unknownSymbols;

    public MoneyFormatter() {
        this.unknownSymbols = Map.of(
                "COP", "$"
        );
    }

    public String withSymbol(BigDecimal amount, CurrencyUnit currency, Locale locale) {
        Money money = Money.of(amount, currency);
        String moneyFormattedWithCode = MonetaryFormats.getAmountFormat(locale).format(money);
        return moneyFormattedWithCode.replace(currency.getCurrencyCode(), getSymbol(currency.getCurrencyCode()));
    }

    public String getSymbol(String currencyCode) {
        Currency jdkCurrency = Currency.getInstance(currencyCode);
        String unknownSymbol = unknownSymbols.get(currencyCode);
        return isNull(unknownSymbol) ? jdkCurrency.getSymbol() : unknownSymbol;
    }
}
