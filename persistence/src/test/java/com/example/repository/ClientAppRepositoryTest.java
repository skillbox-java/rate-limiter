package com.example.repository;

import com.example.AbstractJpaTest;
import com.example.entity.ClientAppEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Тест с использованием H2 базы данных
 */
@EnableJpaRepositories(basePackageClasses = ClientAppRepository.class)
@EntityScan(basePackageClasses = ClientAppEntity.class)
class ClientAppRepositoryTest extends AbstractJpaTest{

    @Autowired
    private ClientAppRepository repo;   // реальный JPA‑репозиторий

    @Test
    @DisplayName("findByApiKey находит клиента по Api Key")
    void findByApiKey() {
        //Arrange: подготовка фикстуры для TestEntityManager
        em.persist(new ClientAppEntity(UUID.randomUUID(), "clientF", "key123"));
        em.persist(new ClientAppEntity(UUID.randomUUID(), "clientA", "key777"));

        //Act
        var client = repo.findByApiKey("key123");

        // Assert
        assertThat(client).isPresent();
        assertThat(client.get())
                .extracting(ClientAppEntity::getName)
                .isEqualTo("clientF");
    }
}