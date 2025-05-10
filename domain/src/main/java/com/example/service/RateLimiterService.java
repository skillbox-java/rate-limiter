package com.example.service;

import com.example.RateLimitDecision;

public interface RateLimiterService {
    RateLimitDecision checkLimit(String apiKey);
    void logRequest(String apiKey);
}
