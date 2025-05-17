package com.example.repository;

import com.example.AbstractJpaTest;
import com.example.entity.ClientAppEntity;
import com.example.entity.RequestLogEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.time.Instant;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Тест с использованием H2 базы данных
 */
@EnableJpaRepositories(basePackageClasses = RequestLogRepository.class)
@EntityScan(basePackageClasses = RequestLogEntity.class)
class RequestLogRepositoryTest extends AbstractJpaTest{

    @Autowired
    private RequestLogRepository repo;   // реальный JPA‑репозиторий

    @Test
    @DisplayName("Считает количество запросов клиента с учетом времени")
    void countRecentRequests() {
        //Arrange: подготовка фикстуры для TestEntityManager
        var clientApp = new ClientAppEntity(UUID.randomUUID(), "clientF", "key123");
        em.persist(clientApp);
        em.persist(new RequestLogEntity(UUID.randomUUID(), clientApp, Instant.parse("2050-12-03T10:15:30.00Z")));
        em.persist(new RequestLogEntity(UUID.randomUUID(), clientApp, Instant.parse("2050-12-03T10:10:30.00Z")));
        em.persist(new RequestLogEntity(UUID.randomUUID(), clientApp, Instant.parse("2050-12-03T10:05:30.00Z")));

        //Act
        var countRecentRequests = repo.countRecentRequests(clientApp, Instant.parse("2050-12-03T10:08:30.00Z"));

        // Assert
        assertThat(countRecentRequests).isEqualTo(2);
    }
}