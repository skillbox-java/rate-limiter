package com.example.repository;

import com.example.entity.ClientAppEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ClientAppRepository extends JpaRepository<ClientAppEntity, UUID> {
    Optional<ClientAppEntity> findByApiKey(String apiKey);
}