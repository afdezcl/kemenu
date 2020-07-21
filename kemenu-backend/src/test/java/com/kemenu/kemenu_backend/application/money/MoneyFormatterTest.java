package com.kemenu.kemenu_backend.application.money;

import org.junit.jupiter.api.Test;

import javax.money.Monetary;
import java.math.BigDecimal;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MoneyFormatterTest {

    private static final MoneyFormatter moneyFormatter = new MoneyFormatter();

    @Test
    void shouldFormatAmounts() {
        String unitedStates = moneyFormatter.withSymbol(BigDecimal.valueOf(1000), Monetary.getCurrency("USD"), Locale.US);
        String colombia = moneyFormatter.withSymbol(BigDecimal.valueOf(1000), Monetary.getCurrency("COP"), new Locale("es", "CO"));
        String germany = moneyFormatter.withSymbol(BigDecimal.valueOf(1000), Monetary.getCurrency("EUR"), Locale.GERMANY);
        String japan = moneyFormatter.withSymbol(BigDecimal.valueOf(1000), Monetary.getCurrency("JPY"), Locale.JAPAN);
        String uk = moneyFormatter.withSymbol(BigDecimal.valueOf(1000), Monetary.getCurrency("GBP"), Locale.UK);

        assertEquals("$1,000.00", unitedStates);
        assertEquals("$ 1.000,00", colombia);
        assertEquals("1.000,00 €", germany);
        assertEquals("¥1,000", japan);
        assertEquals("£1,000.00", uk);
    }
}