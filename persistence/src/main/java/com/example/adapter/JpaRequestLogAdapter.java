package com.example.adapter;

import com.example.RequestLog;
import com.example.entity.ClientAppEntity;
import com.example.entity.RequestLogEntity;
import com.example.port.RequestLogCommandPort;
import com.example.repository.ClientAppRepository;
import com.example.repository.RequestLogRepository;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.UUID;

@Component
public class JpaRequestLogAdapter implements RequestLogCommandPort {

    private final ClientAppRepository clientRepo;
    private final RequestLogRepository logRepo;

    public JpaRequestLogAdapter(ClientAppRepository clientRepo, RequestLogRepository logRepo) {
        this.clientRepo = clientRepo;
        this.logRepo = logRepo;
    }

    @Override
    public long countClientRequestsSince(UUID clientId, Instant since) {
        ClientAppEntity client = clientRepo.findById(clientId)
                .orElseThrow(() -> new IllegalArgumentException("Unknown client ID"));
        return logRepo.countRecentRequests(client, since);
    }

    @Override
    public void save(RequestLog log) {
        ClientAppEntity client = clientRepo.findById(log.clientId())
                .orElseThrow(() -> new IllegalArgumentException("Unknown client ID"));
        RequestLogEntity entity = new RequestLogEntity(log.id(), client, log.timestamp());
        logRepo.save(entity);
    }
}
