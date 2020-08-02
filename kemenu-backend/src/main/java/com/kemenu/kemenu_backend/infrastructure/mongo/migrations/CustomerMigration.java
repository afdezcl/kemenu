package com.kemenu.kemenu_backend.infrastructure.mongo.migrations;

import com.kemenu.kemenu_backend.domain.model.ConfirmedEmail;
import com.kemenu.kemenu_backend.domain.model.ConfirmedEmailRepository;
import com.kemenu.kemenu_backend.domain.model.Customer;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.springframework.context.ApplicationContext;
import org.springframework.data.mongodb.core.mapping.event.AfterConvertCallback;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static java.util.Objects.isNull;

@Slf4j
@Component
@AllArgsConstructor
public class CustomerMigration implements AfterConvertCallback<Customer> {

    private final ApplicationContext applicationContext;

    @Override
    public Customer onAfterConvert(Customer customer, Document document, String collection) {
        if (isNull(customer.getCreatedAt()) && isNull(customer.getUpdatedAt())) {
            confirmEmailOfOldCustomers(customer.getEmail());
            Customer newCustomer = new Customer(customer.getId(), customer.getEmail(), customer.getPassword(), customer.getBusinesses(), customer.getRole());
            newCustomer.putAsOldNewsletterCustomer(); // always is an old customer for newsletter
            return newCustomer;
        } else {
            return tryToConvertToOldNewsletterCustomer(customer);
        }
    }

    private void confirmEmailOfOldCustomers(String email) {
        ConfirmedEmailRepository confirmedEmailRepository = applicationContext.getBean(ConfirmedEmailRepository.class);
        Optional<ConfirmedEmail> optionalConfirmedEmail = confirmedEmailRepository.findByEmail(email);
        if (optionalConfirmedEmail.isEmpty()) {
            ConfirmedEmail confirmedEmail = new ConfirmedEmail(email);
            confirmedEmail.confirm();
            confirmedEmailRepository.save(confirmedEmail);
            log.info("Confirmed email {} for old customer", email);
        }
    }

    private Customer tryToConvertToOldNewsletterCustomer(Customer customer) {
        if (isNull(customer.getMarketingInfo())) {
            customer.putAsOldNewsletterCustomer();
        }

        return customer;
    }
}
