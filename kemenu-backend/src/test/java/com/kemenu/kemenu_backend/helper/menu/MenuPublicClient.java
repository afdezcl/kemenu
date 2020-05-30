package com.kemenu.kemenu_backend.helper.menu;

import com.kemenu.kemenu_backend.application.menu.MenuResponse;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MenuPublicClient {

    private static final String PATH = "/public/v1/short";

    public static List<MenuResponse> read(WebTestClient webTestClient, String shortUrlId) {
        return List.of(webTestClient
                .get().uri(PATH + "/" + shortUrlId)
                .exchange()
                .expectStatus().isOk()
                .expectBody(MenuResponse[].class).returnResult().getResponseBody());
    }
}
