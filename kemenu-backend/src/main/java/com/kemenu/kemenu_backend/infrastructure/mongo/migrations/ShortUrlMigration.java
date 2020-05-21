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

    public Document version0(ShortUrl shortUrl) {
        Document documentV0 = new Document();
        documentV0.append("_id", shortUrl.getId());
        documentV0.append("customerEmail", shortUrl.getCustomerEmail());
        documentV0.append("businessId", shortUrl.getBusinessId());
        documentV0.append("menuId", shortUrl.getMenus().get(0));
        documentV0.append("_class", shortUrl.getClass().getCanonicalName());
        return documentV0;
    }
}
