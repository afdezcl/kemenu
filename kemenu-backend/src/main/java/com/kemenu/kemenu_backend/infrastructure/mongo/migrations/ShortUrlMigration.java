package com.kemenu.kemenu_backend.infrastructure.mongo.migrations;

import com.kemenu.kemenu_backend.domain.model.ShortUrl;
import org.bson.Document;
import org.springframework.data.mongodb.core.mapping.event.AfterConvertCallback;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ShortUrlMigration implements AfterConvertCallback<ShortUrl> {
    @Override
    public ShortUrl onAfterConvert(ShortUrl shortUrl, Document document, String collection) {
        if (document.containsKey("menuId")) {
            List<String> menus = new ArrayList<>();
            menus.add(document.getString("menuId"));
            return new ShortUrl(shortUrl.getId(), shortUrl.getCustomerEmail(), shortUrl.getBusinessId(), menus);
        } else {
            return shortUrl;
        }
    }
}
