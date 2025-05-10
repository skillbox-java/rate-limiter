package com.example;

import java.time.Duration;
import java.util.UUID;

public record RateLimitRule(
        UUID id,
        UUID clientId,
        int maxRequests,
        Duration window
) {}
