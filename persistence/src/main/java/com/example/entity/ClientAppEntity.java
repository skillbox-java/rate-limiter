package com.example.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.UUID;

@Entity
@Table(name = "client_app")
public class ClientAppEntity {

    @Id
    private UUID id;

    private String name;

    @Column(name = "api_key", unique = true, nullable = false)
    private String apiKey;

    protected ClientAppEntity() {
        // for JPA
    }

    public ClientAppEntity(UUID id, String name, String apiKey) {
        this.id = id;
        this.name = name;
        this.apiKey = apiKey;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getApiKey() {
        return apiKey;
    }
}
