package com.example.service;

import com.example.ClientApp;
import com.example.RateLimitRule;
import com.example.RequestLog;
import com.example.entity.ClientAppEntity;
import com.example.entity.RateLimitRuleEntity;
import com.example.entity.RequestLogEntity;

import java.time.Duration;

public class EntityMappers {

    private EntityMappers() {
        // this it utility class
    }

    public static ClientApp toDomain(ClientAppEntity entity) {
        return new ClientApp(entity.getId(), entity.getName(), entity.getApiKey());
    }

    public static RateLimitRule toDomain(RateLimitRuleEntity entity) {
        return new RateLimitRule(
                entity.getId(),
                entity.getClient().getId(),
                entity.getMaxRequests(),
                Duration.ofSeconds(entity.getWindowSeconds())
        );
    }

    public static RequestLog toDomain(RequestLogEntity entity) {
        return new RequestLog(entity.getId(), entity.getClient().getId(), entity.getTimestamp());
    }
}
