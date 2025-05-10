package com.example;

public record RateLimitDecision(
        boolean allowed,
        String reason
) {
    public static RateLimitDecision allowedDecision() {
        return new RateLimitDecision(true, null);
    }

    public static RateLimitDecision deniedDecision(String reason) {
        return new RateLimitDecision(false, reason);
    }
}
