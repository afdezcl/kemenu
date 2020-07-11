package com.kemenu.kemenu_backend.application.money;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Builder;
import lombok.Value;

@Value
@Builder(toBuilder = true)
@JsonDeserialize(builder = CurrencyResponse.CurrencyResponseBuilder.class)
public class CurrencyResponse {
    String isoCode;
    String name;
    String symbol;
}
