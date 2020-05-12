package com.kemenu.kemenu_backend.domain.model;

import com.kemenu.kemenu_backend.helper.BusinessHelper;
import com.kemenu.kemenu_backend.helper.CustomerHelper;
import com.kemenu.kemenu_backend.helper.DishHelper;
import com.kemenu.kemenu_backend.helper.MenuHelper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CustomerTest {

    @Test
    void aCustomerCouldCreateAMenu() {
        Customer customer = CustomerHelper.randomCustomer();
        Business business = BusinessHelper.randomBusiness();

        customer.createMenu(business, MenuHelper.randomMenu());
        customer.createMenu(business, MenuHelper.randomMenu());

        assertEquals(2, customer.menuList(business).size());
    }

    @Test
    void aCustomerCouldCreateAMenuWithSectionAndDish() {
        Customer customer = CustomerHelper.randomCustomer();
        Business business = BusinessHelper.randomBusiness();
        Menu menu = MenuHelper.randomMenu();

        menu.addNewDish("Starters", DishHelper.randomDish());
        menu.addNewDish("Starters", DishHelper.randomDish());
        menu.addNewDish("Starters", DishHelper.randomDish());
        menu.addNewDish("Meats", DishHelper.randomDish());
        menu.addNewDish("Meats", DishHelper.randomDish());
        menu.addNewDish("Fish", DishHelper.randomDish());
        menu.addNewDish("Fish", DishHelper.randomDish());
        menu.addNewDish("Wines", DishHelper.randomDish());
        menu.addNewDish("Desserts", DishHelper.randomDish());
        menu.addNewDish("Desserts", DishHelper.randomDish());
        menu.addNewDish("Desserts", DishHelper.randomDish());
        menu.addNewDish("Desserts", DishHelper.randomDish());
        menu.addNewDish("Desserts", DishHelper.randomDish());

        String menuId = customer.createMenu(business, menu);

        assertEquals(menu.getId(), menuId);
        assertEquals(1, customer.menuList(business).size());
        assertEquals(5, menu.numberOfSections());
        assertEquals(3, menu.numberOfDishes("Starters"));
        assertEquals(2, menu.numberOfDishes("Meats"));
        assertEquals(2, menu.numberOfDishes("Fish"));
        assertEquals(1, menu.numberOfDishes("Wines"));
        assertEquals(5, menu.numberOfDishes("Desserts"));
        assertEquals(0, menu.numberOfDishes("Non existing section"));
    }
}