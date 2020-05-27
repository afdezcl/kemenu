package com.kemenu.kemenu_backend.helper.customer;

import com.kemenu.kemenu_backend.application.customer.CustomerRequest;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;

import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CustomerRequestHelper {

    public static CustomerRequest randomRequest() {
        return CustomerRequest.builder()
                .businessName(UUID.randomUUID().toString())
                .email("test@example.com")
                .password(UUID.randomUUID().toString())
                .recaptchaToken(UUID.randomUUID().toString())
                .lang("en")
                .build();
    }

    public static CustomerRequest withWrongEmail() {
        return randomRequest().toBuilder().email("asd@asd").build();
    }

    public static MultipartBodyBuilder photo() {
        MultipartBodyBuilder bodyBuilder = new MultipartBodyBuilder();
        bodyBuilder.part("file", new ClassPathResource("kemenu.png")).contentType(MediaType.MULTIPART_FORM_DATA);
        return bodyBuilder;
    }
}
