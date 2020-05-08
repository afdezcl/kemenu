package com.kemenu.kemenu_backend.helper;

import com.kemenu.kemenu_backend.domain.model.Customer;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CustomerHelper {

    public static Customer randomCustomer() {
        return new Customer(UUID.randomUUID().toString(), UUID.randomUUID().toString());
    }
}
