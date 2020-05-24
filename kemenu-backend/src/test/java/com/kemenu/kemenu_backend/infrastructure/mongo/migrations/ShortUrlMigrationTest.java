package com.kemenu.kemenu_backend.infrastructure.mongo.migrations;

import com.kemenu.kemenu_backend.common.KemenuIntegrationTest;
import com.kemenu.kemenu_backend.domain.model.ShortUrl;
import com.kemenu.kemenu_backend.domain.model.ShortUrlRepository;
import com.kemenu.kemenu_backend.helper.short_url.ShortUrlDocumentHelper;
import com.kemenu.kemenu_backend.helper.short_url.ShortUrlHelper;
import org.bson.Document;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ShortUrlMigrationTest extends KemenuIntegrationTest {

    @Autowired
    private ShortUrlRepository shortUrlRepository;

    @Test
    void anOldShortUrlVersion0IsCorrectlyMigrated() {
        Document oldShortUrl = ShortUrlDocumentHelper.version0(ShortUrlHelper.random());
        mongoTemplate.save(oldShortUrl, "shortUrl");
        ShortUrl newInMemoryShortUrl = shortUrlRepository.findById(oldShortUrl.getString("_id")).get();
        shortUrlRepository.save(newInMemoryShortUrl);
        ShortUrl newPersistedShortUrl = shortUrlRepository.findById(oldShortUrl.getString("_id")).get();
        assertEquals(oldShortUrl.getString("_id"), newPersistedShortUrl.getId());
        assertEquals(newInMemoryShortUrl.getMenus().get(0), newPersistedShortUrl.getMenus().get(0));
        assertTrue(newPersistedShortUrl.getMenus().size() > 0);
    }
}