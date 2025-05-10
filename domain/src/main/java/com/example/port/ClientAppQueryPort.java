package com.example.port;

import com.example.ClientApp;

import java.util.Optional;

public interface ClientAppQueryPort {
    Optional<ClientApp> findByApiKey(String apiKey);
}
