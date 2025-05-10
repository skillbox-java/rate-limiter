package com.example.controller.dto.response;

/**
 * @param clientName имя клиента
 * @param apiKey     ключ доступа к API
 */
public record RegistrationResponse(

        String clientName,
        String apiKey
) {
}
