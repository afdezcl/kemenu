package com.kemenu.kemenu_backend.common;

import com.mongodb.BasicDBObject;
import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles(profiles = "test")
public class MongoTest {

    @Autowired
    private MongoTemplate mongoTemplate;

    @AfterEach
    void tearDown() {
        mongoTemplate.getCollectionNames().stream()
                .filter(collection -> !collection.startsWith("system."))
                .forEach(collection -> mongoTemplate.getCollection(collection).deleteOne(new BasicDBObject()));
    }
}
