package com.example.port;

import com.example.RateLimitRule;

public interface RateLimitRuleCommandPort {
    void save(RateLimitRule rule);
}
