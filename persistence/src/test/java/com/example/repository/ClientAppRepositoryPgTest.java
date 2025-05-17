package com.example.repository;

import com.example.config.PersistenceTestConfig;
import com.example.entity.ClientAppEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Тест с использованием настоящей БД через TestContainers
 */
@DataJpaTest

@Testcontainers
@EnableJpaRepositories(basePackageClasses = ClientAppRepository.class)
@EntityScan(basePackageClasses = ClientAppEntity.class)
@ContextConfiguration(classes = PersistenceTestConfig.class)
class ClientAppRepositoryPgTest {

    @Container
    @ServiceConnection                 // 3.1+: автоматически регистрирует контейнер в контексте
    static PostgreSQLContainer<?> pg = new PostgreSQLContainer<>("postgres:16-alpine");

    @DynamicPropertySource         // если нужен fine‑tune параметров
    static void postgresProps(DynamicPropertyRegistry r) {
        r.add("spring.jpa.show-sql", () -> true);
    }

    @Autowired
    protected TestEntityManager em;  // доступен во всех наследниках

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