package com.kemenu.kemenu_backend.common;

import com.mongodb.BasicDBObject;
import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MongoTest {

    @Autowired
    private MongoTemplate mongoTemplate;

    @AfterEach
    void tearDown() {
        for (String collectionName : mongoTemplate.getCollectionNames()) {
            if (!collectionName.startsWith("system.")) {
                mongoTemplate.getCollection(collectionName).deleteOne(new BasicDBObject());
            }
        }
    }
}
