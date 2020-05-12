package com.kemenu.kemenu_backend.domain.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import org.springframework.data.annotation.PersistenceConstructor;

import java.util.List;

@Value
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE, onConstructor = @__(@PersistenceConstructor))
public class MenuSection {
    String name;
    List<Dish> dishes;

    public void addNewDish(Dish dish) {
        dishes.add(dish);
    }

    public int numberOfDishes() {
        return dishes.size();
    }
}
