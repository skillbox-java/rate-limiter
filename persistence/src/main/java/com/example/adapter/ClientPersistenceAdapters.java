package com.example.adapter;

import com.example.ClientApp;
import com.example.RateLimitRule;
import com.example.entity.ClientAppEntity;
import com.example.entity.RateLimitRuleEntity;
import com.example.port.ClientAppCommandPort;
import com.example.port.RateLimitRuleCommandPort;
import com.example.repository.ClientAppRepository;
import com.example.repository.RateLimitRuleRepository;
import org.springframework.stereotype.Component;

@Component
public class ClientPersistenceAdapters implements ClientAppCommandPort, RateLimitRuleCommandPort {

    private final ClientAppRepository clientRepo;
    private final RateLimitRuleRepository ruleRepo;

    public ClientPersistenceAdapters(ClientAppRepository clientRepo, RateLimitRuleRepository ruleRepo) {
        this.clientRepo = clientRepo;
        this.ruleRepo = ruleRepo;
    }

    @Override
    public void save(ClientApp client) {
        ClientAppEntity entity = new ClientAppEntity(client.id(), client.name(), client.apiKey());
        clientRepo.save(entity);
    }

    @Override
    public void save(RateLimitRule rule) {
        ClientAppEntity client = clientRepo.findById(rule.clientId())
                .orElseThrow(() -> new IllegalArgumentException("Client not found"));

        RateLimitRuleEntity entity = new RateLimitRuleEntity(
                rule.id(),
                client,
                rule.maxRequests(),
                (int) rule.window().getSeconds()
        );

        ruleRepo.save(entity);
    }
}
