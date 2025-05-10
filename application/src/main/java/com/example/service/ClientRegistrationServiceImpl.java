package com.example.service;

import com.example.ClientApp;
import com.example.RateLimitRule;
import com.example.port.ClientAppCommandPort;
import com.example.port.RateLimitRuleCommandPort;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.UUID;

@Service
public class ClientRegistrationServiceImpl implements ClientRegistrationService {
    private final ClientAppCommandPort clientCommand;
    private final RateLimitRuleCommandPort ruleCommand;

    @Value("${rate.rules.default.maxRequests:5}")
    int maxRequests;

    @Value("${rate.rules.default.timeWindow:PT60S}")
    Duration timeWindow;

    public ClientRegistrationServiceImpl(
            ClientAppCommandPort clientCommand,
            RateLimitRuleCommandPort ruleCommand
    ) {
        this.clientCommand = clientCommand;
        this.ruleCommand = ruleCommand;
    }

    @Override
    public ClientApp register(String name) {
        UUID id = UUID.randomUUID();
        String apiKey = UUID.randomUUID().toString();

        ClientApp client = new ClientApp(id, name, apiKey);
        clientCommand.save(client);

        RateLimitRule rule = new RateLimitRule(
                UUID.randomUUID(),
                id,
                maxRequests,
                timeWindow
        );
        ruleCommand.save(rule);

        return client;
    }
}
