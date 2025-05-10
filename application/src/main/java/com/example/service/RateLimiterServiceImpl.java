package com.example.service;

import com.example.ClientApp;
import com.example.RateLimitDecision;
import com.example.RateLimitRule;
import com.example.RequestLog;
import com.example.port.ClientAppQueryPort;
import com.example.port.RateLimitRuleQueryPort;
import com.example.port.RequestLogCommandPort;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
public class RateLimiterServiceImpl implements RateLimiterService {

    private final ClientAppQueryPort clientAppQuery;
    private final RateLimitRuleQueryPort ruleQuery;
    private final RequestLogCommandPort logCommand;

    public RateLimiterServiceImpl(
            ClientAppQueryPort clientAppQuery,
            RateLimitRuleQueryPort ruleQuery,
            RequestLogCommandPort logCommand
    ) {
        this.clientAppQuery = clientAppQuery;
        this.ruleQuery = ruleQuery;
        this.logCommand = logCommand;
    }

    @Override
    public RateLimitDecision checkLimit(String apiKey) {
        ClientApp client = clientAppQuery.findByApiKey(apiKey)
                .orElseThrow(() -> new IllegalArgumentException("Invalid API key"));

        RateLimitRule rule = ruleQuery.findByClientId(client.id())
                .orElseThrow(() -> new IllegalStateException("No rule defined for client"));

        Instant windowStart = Instant.now().minus(rule.window());
        long recent = logCommand.countClientRequestsSince(client.id(), windowStart);

        return recent >= rule.maxRequests()
                ? RateLimitDecision.deniedDecision("Rate limit exceeded")
                : RateLimitDecision.allowedDecision();
    }

    @Override
    public void logRequest(String apiKey) {
        ClientApp client = clientAppQuery.findByApiKey(apiKey)
                .orElseThrow(() -> new IllegalArgumentException("Invalid API key"));
        logCommand.save(new RequestLog(UUID.randomUUID(), client.id(), Instant.now()));
    }
}
