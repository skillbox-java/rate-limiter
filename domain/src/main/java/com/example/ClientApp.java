package com.example;


import com.example.exception.ValidationException;

import java.util.Objects;
import java.util.UUID;

public record ClientApp(UUID id, String name, String apiKey) {

    public ClientApp {
        if(Objects.isNull(id)){
            throw new ValidationException("id не может быть null");
        }

        if(Objects.isNull(name)){
            throw new ValidationException("name не может быть null");
        }

        if(name.isBlank()){
            throw new ValidationException("name не может быть пустым");
        }

    }
}
