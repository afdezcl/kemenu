package com.kemenu.kemenu_backend.helper;

import com.kemenu.kemenu_backend.domain.model.Customer;
import com.kemenu.kemenu_backend.domain.model.Menu;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CustomerHelper {

    public static Customer randomCustomer() {
        return new Customer(UUID.randomUUID().toString() + "@example.com", UUID.randomUUID().toString(), UUID.randomUUID().toString());
    }

    public static Customer withMenu() {
        Menu menu = MenuHelper.randomMenu();
        menu.addNewDish(UUID.randomUUID().toString(), DishHelper.randomDish());
        menu.addNewDish(UUID.randomUUID().toString(), DishHelper.randomDish());
        menu.addNewDish(UUID.randomUUID().toString(), DishHelper.randomDish());
        Customer customer = randomCustomer();
        customer.createMenu(customer.getFirstBusiness(), menu);
        return customer;
    }

    public static Customer randomAdmin() {
        return new Customer(
                "admin@example.com",
                "admin",
                Customer.Role.ADMIN,
                "adminBusiness"
        );
    }
}
