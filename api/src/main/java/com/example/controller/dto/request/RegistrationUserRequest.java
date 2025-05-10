package com.example.controller.dto.request;

/**
 * Регистрация нового пользователя API
 *
 * @param name имя пользователя
 */
public record RegistrationUserRequest(
        String name
) {
}
