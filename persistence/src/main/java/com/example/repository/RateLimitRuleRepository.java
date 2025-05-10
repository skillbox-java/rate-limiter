package com.example.repository;

import com.example.entity.ClientAppEntity;
import com.example.entity.RateLimitRuleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface RateLimitRuleRepository extends JpaRepository<RateLimitRuleEntity, UUID> {
    Optional<RateLimitRuleEntity> findByClient(ClientAppEntity client);
}
