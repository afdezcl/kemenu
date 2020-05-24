package com.kemenu.kemenu_backend.common;

import com.kemenu.kemenu_backend.application.security.JWTService;
import com.kemenu.kemenu_backend.domain.model.Customer;
import com.kemenu.kemenu_backend.helper.customer.CustomerHelper;
import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles(profiles = "test")
public class KemenuIntegrationTest {

    protected static final Customer randomCustomer = CustomerHelper.randomCustomer();
    protected static final String[] customerRole = {randomCustomer.getAuthority()};

    @Autowired
    protected WebTestClient webTestClient;

    @Autowired
    protected MongoTemplate mongoTemplate;

    @Autowired
    protected JWTService jwtService;

    @AfterEach
    void tearDown() {
        mongoTemplate.getCollectionNames().stream()
                .filter(collection -> !collection.startsWith("system."))
                .forEach(collection -> mongoTemplate.getCollection(collection).deleteMany(Criteria.where("email").ne("admin@example.com").getCriteriaObject()));
    }

    protected String generateAccessToken() {
        return "Bearer " + jwtService.generateAccessToken(randomCustomer.getEmail(), customerRole);
    }

    protected String generateAccessToken(String email) {
        return "Bearer " + jwtService.generateAccessToken(email, customerRole);
    }

    protected String generateExpiredAccessToken() {
        return "Bearer " + jwtService.generateAccessToken(randomCustomer.getEmail(), -9000, customerRole);
    }

    protected String generateRefreshToken() {
        return "Bearer " + jwtService.generateRefreshToken(randomCustomer.getEmail(), customerRole);
    }

    protected String generateExpiredRefreshToken() {
        return "Bearer " + jwtService.generateRefreshToken(randomCustomer.getEmail(), -9000, customerRole);
    }
}
