package com.example.e2e;

import com.example.controller.dto.request.RegistrationUserRequest;
import com.example.controller.dto.response.RegistrationResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.assertj.core.api.Assertions.assertThat;

// Запускаем полное Spring Boot приложение на случайном порту
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
// Включаем поддержку Testcontainers
@Testcontainers
class RegisterClientTest {

    // Настраиваем контейнер PostgreSQL, который будет запущен для тестов
    @Container
    @ServiceConnection // 3.1+: автоматически регистрирует контейнер в контексте
    static PostgreSQLContainer<?> pg = new PostgreSQLContainer<>("postgres:16-alpine");

    // Если нужно, можно добавить динамические свойства, например, для логирования SQL
    @DynamicPropertySource
    static void postgresProps(DynamicPropertyRegistry r) {
        r.add("spring.jpa.show-sql", () -> true);
    }

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    // Метод, который будет выполняться после каждого тестового метода.
    // Используется для очистки базы данных, чтобы тесты были независимы.

    @Test
    @DisplayName("При регистрации получен API ключ")
    void register_client_and_receive_apikey() {
        // Act
        String createUrl = "http://localhost:" + port + "/api/register";
        RegistrationUserRequest request = new RegistrationUserRequest("Bob");
        ResponseEntity<RegistrationResponse> createResponse =
                restTemplate.postForEntity(createUrl, request, RegistrationResponse.class);

        // Assert
        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(createResponse.getBody()).isNotNull();
    }

    @Test
    @DisplayName("При регистрации не передали имя -> ошибка")
    void when_register_name_is_null_in_request_then_error() {
        // Act
        String createUrl = "http://localhost:" + port + "/api/register";
        RegistrationUserRequest request = new RegistrationUserRequest(null);
        ResponseEntity<RegistrationResponse> createResponse =
                restTemplate.postForEntity(createUrl, request, RegistrationResponse.class);

        // Assert
        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        assertThat(createResponse.getBody()).isNotNull();
    }
}
