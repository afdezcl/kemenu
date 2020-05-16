package com.kemenu.kemenu_backend.helper;

import com.kemenu.kemenu_backend.domain.model.Customer;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CustomerHelper {

    public static Customer randomCustomer() {
        return new Customer(UUID.randomUUID().toString() + "@example.com", UUID.randomUUID().toString(), UUID.randomUUID().toString());
    }

    public static Customer withMenu() {
        Customer customer = randomCustomer();
        customer.createMenu(customer.firstBusiness(), MenuHelper.randomMenu());
        return customer;
    }
}
