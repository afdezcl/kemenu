package com.kemenu.kemenu_backend.helper.menu;

import com.kemenu.kemenu_backend.application.menu.CreateMenuRequest;
import com.kemenu.kemenu_backend.application.menu.CreateMenuResponse;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MenuWebClient {

    private static final String PATH = "/web/v1/menus";

    public static CreateMenuRequest request;

    public static CreateMenuResponse create(WebTestClient webTestClient,
                                            String businessId,
                                            String token) {
        request = MenuRequestHelper.randomRequest(businessId);
        return webTestClient
                .post().uri(PATH)
                .body(Mono.just(request), CreateMenuRequest.class)
                .header("Authorization", token)
                .exchange()
                .expectStatus().isOk()
                .expectBody(CreateMenuResponse.class).returnResult().getResponseBody();
    }
}
