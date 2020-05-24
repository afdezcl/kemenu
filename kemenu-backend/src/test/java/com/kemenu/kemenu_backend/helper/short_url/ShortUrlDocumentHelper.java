package com.kemenu.kemenu_backend.helper.short_url;

import com.kemenu.kemenu_backend.domain.model.ShortUrl;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.bson.Document;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ShortUrlDocumentHelper {

    public static Document version0(ShortUrl shortUrl) {
        Document documentV0 = new Document();
        documentV0.append("_id", shortUrl.getId());
        documentV0.append("customerEmail", shortUrl.getCustomerEmail());
        documentV0.append("businessId", shortUrl.getBusinessId());
        documentV0.append("menuId", shortUrl.getMenus().get(0));
        documentV0.append("_class", shortUrl.getClass().getCanonicalName());
        return documentV0;
    }
}
