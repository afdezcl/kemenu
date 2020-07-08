package com.kemenu.kemenu_backend.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import org.springframework.data.annotation.PersistenceConstructor;

import javax.money.CurrencyUnit;
import javax.money.Monetary;
import java.util.List;

import static java.util.Objects.isNull;

@Value
@Builder(toBuilder = true)
@AllArgsConstructor(onConstructor = @__(@PersistenceConstructor))
public class Menu {

    String id;
    List<MenuSection> sections;
    @Builder.Default
    String imageUrl = "";
    @Builder.Default
    CurrencyUnit currency = Monetary.getCurrency("EUR");

    public CurrencyUnit getCurrency() {
        return isNull(currency) ? Monetary.getCurrency("EUR") : currency; // TODO: This should be in migrations?
    }
}
