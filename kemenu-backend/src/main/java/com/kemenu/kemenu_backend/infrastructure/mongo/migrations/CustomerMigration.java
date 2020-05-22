package com.kemenu.kemenu_backend.infrastructure.mongo.migrations;

import com.kemenu.kemenu_backend.domain.model.Customer;
import org.bson.Document;
import org.springframework.data.mongodb.core.mapping.event.AfterConvertCallback;
import org.springframework.stereotype.Component;

import static java.util.Objects.isNull;

@Component
public class CustomerMigration implements AfterConvertCallback<Customer> {
    @Override
    public Customer onAfterConvert(Customer customer, Document document, String collection) {
        if (isNull(customer.getCreatedAt()) && isNull(customer.getUpdatedAt())) {
            return new Customer(customer.getId(), customer.getEmail(), customer.getPassword(), customer.getBusinesses(), customer.getRole());
        } else {
            return customer;
        }
    }
}
