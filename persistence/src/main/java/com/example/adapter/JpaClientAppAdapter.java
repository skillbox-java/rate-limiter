package com.example.adapter;

import com.example.ClientApp;
import com.example.port.ClientAppQueryPort;
import com.example.repository.ClientAppRepository;
import com.example.service.EntityMappers;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class JpaClientAppAdapter implements ClientAppQueryPort {

    private final ClientAppRepository clientRepo;

    public JpaClientAppAdapter(ClientAppRepository clientRepo) {
        this.clientRepo = clientRepo;
    }

    @Override
    public Optional<ClientApp> findByApiKey(String apiKey) {
        return clientRepo.findByApiKey(apiKey)
                .map(EntityMappers::toDomain);
    }
}
