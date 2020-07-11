package com.kemenu.kemenu_backend.application.money;

import com.kemenu.kemenu_backend.common.KemenuIntegrationTest;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CurrencyIntegrationTest extends KemenuIntegrationTest {

    @Test
    void shouldReturnAllCurrenciesInSpanish() {
        List<CurrencyResponse> currencies = List.of(
                webTestClient
                        .get().uri("/web/v1/currencies")
                        .header("Authorization", generateAccessToken())
                        .header("Accept-Language", "es-CO,es;q=0.5")
                        .exchange()
                        .expectStatus().isOk()
                        .expectBody(CurrencyResponse[].class).returnResult().getResponseBody()
        );

        assertEquals("peso colombiano", currencies.stream().filter(c -> c.getIsoCode().equals("COP")).findFirst().get().getName());
    }

    @Test
    void shouldReturnAllCurrenciesInEnglish() {
        List<CurrencyResponse> currencies = List.of(
                webTestClient
                        .get().uri("/web/v1/currencies")
                        .header("Authorization", generateAccessToken())
                        .header("Accept-Language", "en-US,en;q=0.5")
                        .exchange()
                        .expectStatus().isOk()
                        .expectBody(CurrencyResponse[].class).returnResult().getResponseBody()
        );

        assertEquals("Colombian Peso", currencies.stream().filter(c -> c.getIsoCode().equals("COP")).findFirst().get().getName());
    }
}
