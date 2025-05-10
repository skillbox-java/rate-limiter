package com.example.entity;

import jakarta.persistence.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "request_log")
public class RequestLogEntity {

    @Id
    private UUID id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "client_id")
    private ClientAppEntity client;

    private Instant timestamp;

    protected RequestLogEntity() {
    }

    public RequestLogEntity(UUID id, ClientAppEntity client, Instant timestamp) {
        this.id = id;
        this.client = client;
        this.timestamp = timestamp;
    }

    public UUID getId() {
        return id;
    }

    public ClientAppEntity getClient() {
        return client;
    }

    public Instant getTimestamp() {
        return timestamp;
    }
}
