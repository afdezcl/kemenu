package com.kemenu.kemenu_backend.common;

import com.auth0.jwt.JWT;
import com.mongodb.BasicDBObject;
import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.Date;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles(profiles = "test")
public class KemenuIntegrationTest {

    @Value("${app.secret}")
    private String appSecret;

    @Autowired
    protected WebTestClient webTestClient;

    @Autowired
    protected MongoTemplate mongoTemplate;

    @AfterEach
    void tearDown() {
        mongoTemplate.getCollectionNames().stream()
                .filter(collection -> !collection.startsWith("system."))
                .forEach(collection -> mongoTemplate.getCollection(collection).deleteOne(new BasicDBObject()));
    }

    protected String generateAccessToken() {
        String[] roles = {"ROLE_USER"};

        return "Bearer " + JWT.create()
                .withSubject("testusername")
                .withExpiresAt(new Date(System.currentTimeMillis() + 900000))
                .withArrayClaim("role", roles)
                .sign(HMAC512(appSecret.getBytes()));
    }
}
