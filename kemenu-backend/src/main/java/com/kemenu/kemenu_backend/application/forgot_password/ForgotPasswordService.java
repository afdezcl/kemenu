package com.kemenu.kemenu_backend.application.forgot_password;

import com.kemenu.kemenu_backend.application.customer.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ForgotPasswordService {

    private final CustomerService customerService;

    public void sendForgotPasswordEmail(String email, String lang) {
        customerService.read(email)
                .ifPresent(c -> {

                });
    }


}
