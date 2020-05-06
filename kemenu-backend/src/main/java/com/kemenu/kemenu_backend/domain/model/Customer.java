package com.kemenu.kemenu_backend.domain.model;

import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Getter
@Document
public class Customer {

    @Id
    private String id;
    private String name;

    public Customer(String name) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
    }
}
