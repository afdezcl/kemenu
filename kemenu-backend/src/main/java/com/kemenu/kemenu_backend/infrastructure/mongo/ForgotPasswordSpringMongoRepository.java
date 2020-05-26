package com.kemenu.kemenu_backend.infrastructure.mongo;

import com.kemenu.kemenu_backend.application.forgot_password.ForgotPassword;
import org.springframework.data.mongodb.repository.MongoRepository;

interface ForgotPasswordSpringMongoRepository extends MongoRepository<ForgotPassword, String> {
}
