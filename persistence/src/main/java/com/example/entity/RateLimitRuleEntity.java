package com.example.entity;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "rate_limit_rule")
public class RateLimitRuleEntity {

    @Id
    private UUID id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "client_id")
    private ClientAppEntity client;

    @Column(name = "max_requests")
    private int maxRequests;

    @Column(name = "window_seconds")
    private int windowSeconds;

    protected RateLimitRuleEntity() {
    }

    public RateLimitRuleEntity(UUID id, ClientAppEntity client, int maxRequests, int windowSeconds) {
        this.id = id;
        this.client = client;
        this.maxRequests = maxRequests;
        this.windowSeconds = windowSeconds;
    }

    public UUID getId() {
        return id;
    }

    public ClientAppEntity getClient() {
        return client;
    }

    public int getMaxRequests() {
        return maxRequests;
    }

    public int getWindowSeconds() {
        return windowSeconds;
    }
}