package com.kemenu.kemenu_backend.domain.model;

import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder(toBuilder = true)
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
