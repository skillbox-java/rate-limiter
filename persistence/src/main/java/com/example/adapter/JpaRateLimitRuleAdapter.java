package com.example.adapter;

import com.example.RateLimitRule;
import com.example.port.RateLimitRuleQueryPort;
import com.example.repository.RateLimitRuleRepository;
import com.example.service.EntityMappers;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class JpaRateLimitRuleAdapter implements RateLimitRuleQueryPort {

    private final RateLimitRuleRepository ruleRepo;

    public JpaRateLimitRuleAdapter(RateLimitRuleRepository ruleRepo) {
        this.ruleRepo = ruleRepo;
    }

    @Override
    public Optional<RateLimitRule> findByClientId(UUID clientId) {
        return ruleRepo.findAll().stream()
                .filter(rule -> rule.getClient().getId().equals(clientId))
                .findFirst()
                .map(EntityMappers::toDomain);
    }
}