package com.kemenu.kemenu_backend.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import org.springframework.data.annotation.PersistenceConstructor;

import javax.money.CurrencyUnit;
import javax.money.Monetary;
import java.util.List;

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

    public Menu changeCurrency(String newCurrency) {
        return toBuilder().currency(Monetary.getCurrency(newCurrency)).build();
    }
}
