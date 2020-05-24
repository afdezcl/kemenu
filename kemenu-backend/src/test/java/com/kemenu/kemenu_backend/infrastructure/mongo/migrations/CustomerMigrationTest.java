package com.kemenu.kemenu_backend.infrastructure.mongo.migrations;

import com.kemenu.kemenu_backend.common.KemenuIntegrationTest;
import com.kemenu.kemenu_backend.domain.model.ConfirmedEmail;
import com.kemenu.kemenu_backend.domain.model.ConfirmedEmailRepository;
import com.kemenu.kemenu_backend.domain.model.Customer;
import com.kemenu.kemenu_backend.domain.model.CustomerRepository;
import com.kemenu.kemenu_backend.helper.customer.CustomerDocumentHelper;
import com.kemenu.kemenu_backend.helper.customer.CustomerHelper;
import org.bson.Document;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CustomerMigrationTest extends KemenuIntegrationTest {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ConfirmedEmailRepository confirmedEmailRepository;

    @Test
    void anOldCustomerVersion0IsCorrectlyMigrated() {
        Document oldCustomer = CustomerDocumentHelper.version0(CustomerHelper.withMenu());
        mongoTemplate.save(oldCustomer, "customer");
        Customer newInMemoryCustomer = customerRepository.findById(oldCustomer.getString("_id")).get();
        customerRepository.save(newInMemoryCustomer);
        Customer newPersistedCustomer = customerRepository.findById(oldCustomer.getString("_id")).get();
        ConfirmedEmail confirmedEmail = confirmedEmailRepository.findByEmail(newPersistedCustomer.getEmail()).get();

        assertEquals(oldCustomer.getString("_id"), newPersistedCustomer.getId());
        assertEquals(newInMemoryCustomer.getBusinesses().get(0), newPersistedCustomer.getBusinesses().get(0));
        assertTrue(Objects.nonNull(newPersistedCustomer.getCreatedAt()));
        assertTrue(Objects.nonNull(newPersistedCustomer.getUpdatedAt()));
        assertTrue(confirmedEmail.isConfirmed());
    }
}