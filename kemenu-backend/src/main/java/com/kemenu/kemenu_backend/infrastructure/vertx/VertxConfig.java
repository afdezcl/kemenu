package com.kemenu.kemenu_backend.infrastructure.vertx;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kemenu.kemenu_backend.application.email.EmailEventSubscriber;
import com.kemenu.kemenu_backend.application.email.EmailService;
import com.kemenu.kemenu_backend.domain.model.ConfirmedEmailRepository;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.EventBus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Slf4j
@Configuration
class VertxConfig {

    @Value("${app.cors}")
    private List<String> allowedOrigins;

    @Bean
    Vertx vertx() {
        return Vertx.vertx();
    }

    @Bean
    EventBus eventBus() {
        return vertx().eventBus();
    }

    @Bean
    CommandLineRunner initVerticles(Vertx vertx, EventBus eventBus, ObjectMapper mapper, ConfirmedEmailRepository confirmedEmailRepository, EmailService emailService) {
        return args -> {
            DeploymentOptions workerVerticleOptions = new DeploymentOptions().setWorker(true);
            for (int i = 0; i < Runtime.getRuntime().availableProcessors(); i++) {
                vertx.deployVerticle(new EmailEventSubscriber(eventBus, mapper, confirmedEmailRepository, allowedOrigins.get(0), emailService), workerVerticleOptions);
            }
            log.info("Verticles deployed");
        };
    }
}
