package com.kemenu.kemenu_backend.infrastructure.mongo;

import com.kemenu.kemenu_backend.application.forgot_password.ForgotPassword;
import com.kemenu.kemenu_backend.application.forgot_password.ForgotPasswordRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@AllArgsConstructor
class ForgotPasswordMongoRepository implements ForgotPasswordRepository {

    private final ForgotPasswordSpringMongoRepository springMongoRepository;

    @Override
    public Optional<ForgotPassword> findById(String id) {
        return springMongoRepository.findById(id);
    }

    @Override
    public String save(ForgotPassword forgotPassword) {
        return springMongoRepository.save(forgotPassword).getId();
    }
}
