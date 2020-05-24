package com.kemenu.kemenu_backend.helper.short_url;

import com.kemenu.kemenu_backend.domain.model.ShortUrl;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ShortUrlHelper {
    public static ShortUrl random() {
        var menus = new ArrayList<String>();
        menus.add(UUID.randomUUID().toString());
        return new ShortUrl(
                UUID.randomUUID().toString(),
                UUID.randomUUID().toString(),
                UUID.randomUUID().toString(),
                menus
        );
    }
}
