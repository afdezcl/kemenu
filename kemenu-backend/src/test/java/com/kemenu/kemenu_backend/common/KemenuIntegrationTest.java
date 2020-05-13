package com.kemenu.kemenu_backend.common;

import com.kemenu.kemenu_backend.application.security.JWTService;
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

    private static final String[] USER_ROLE = {"ROLE_USER"};

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
                .forEach(collection -> mongoTemplate.getCollection(collection).deleteMany(Criteria.where("email").ne("admin").getCriteriaObject()));
    }

    protected String generateAccessToken() {
        return "Bearer " + jwtService.generateAccessToken("testusername", USER_ROLE);
    }

    protected String generateExpiredAccessToken() {
        return "Bearer " + jwtService.generateAccessToken("testusername", -9000, USER_ROLE);
    }

    protected String generateRefreshToken() {
        return "Bearer " + jwtService.generateRefreshToken("testusername", USER_ROLE);
    }

    protected String generateExpiredRefreshToken() {
        return "Bearer " + jwtService.generateRefreshToken("testusername", -9000, USER_ROLE);
    }
}
