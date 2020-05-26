package com.kemenu.kemenu_backend.application.forgot_password;

import java.util.Optional;

public interface ForgotPasswordRepository {
    Optional<ForgotPassword> findById(String id);

    String save(ForgotPassword customer);
}
