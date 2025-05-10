package com.example.port;

import com.example.RateLimitRule;

import java.util.Optional;
import java.util.UUID;

public interface RateLimitRuleQueryPort {
    Optional<RateLimitRule> findByClientId(UUID clientId);
}
