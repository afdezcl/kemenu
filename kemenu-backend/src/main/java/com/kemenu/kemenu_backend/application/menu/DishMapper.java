package com.kemenu.kemenu_backend.application.menu;

import com.kemenu.kemenu_backend.application.allergen.AllergenData;
import com.kemenu.kemenu_backend.application.money.MoneyFormatter;
import com.kemenu.kemenu_backend.domain.model.MenuSection;
import com.kemenu.kemenu_backend.infrastructure.cloudinary.CloudinaryService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.money.CurrencyUnit;
import java.util.List;
import java.util.Locale;

import static java.util.Objects.isNull;
import static java.util.stream.Collectors.toList;

@Component
@AllArgsConstructor
public class DishMapper {

    private final CloudinaryService cloudinaryService;
    private final MoneyFormatter moneyFormatter;

    public List<DishResponse> from(MenuSection menuSection, CurrencyUnit currency, Locale locale) {
        return menuSection.getDishes().stream()
                .map(d -> DishResponse.builder()
                        .name(d.getName())
                        .description(d.getDescription())
                        .price(d.getPrice())
                        .formattedPrice(moneyFormatter.withSymbol(d.getPrice(), currency, locale))
                        .allergens(d.getAllergens().stream().map(a -> AllergenData.builder().id(a.getId()).name(a.getName()).build()).collect(toList()))
                        .imageUrl(!StringUtils.isEmpty(d.getImageUrl()) ? cloudinaryService.getOptimizedUrl(d.getImageUrl()) : "")
                        .available(isNull(d.getAvailable()) ? true : d.getAvailable()) // TODO: Refactor when frontend use it
                        .build()
                )
                .collect(toList());
    }
}
