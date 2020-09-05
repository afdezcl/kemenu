package com.kemenu.kemenu_backend.helper.money;

import com.kemenu.kemenu_backend.application.money.CurrencyResponse;
import com.kemenu.kemenu_backend.application.money.CurrencyService;
import com.kemenu.kemenu_backend.application.money.MoneyFormatter;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Locale;
import java.util.concurrent.ThreadLocalRandom;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MoneyHelper {

    private static final CurrencyService currencyService = new CurrencyService(new MoneyFormatter());

    public static List<CurrencyResponse> allCurrencies() {
        return currencyService.allCurrencies(Locale.ENGLISH);
    }

    public static CurrencyResponse random() {
        List<CurrencyResponse> allCurrencies = allCurrencies();
        int randomId = ThreadLocalRandom.current().nextInt(0, allCurrencies.size());
        return allCurrencies.get(randomId);
    }

    public static String randomCurrencyCode() {
        return random().getIsoCode();
    }
}
