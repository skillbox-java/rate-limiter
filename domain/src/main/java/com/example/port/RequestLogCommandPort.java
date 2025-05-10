package com.example.port;

import com.example.RequestLog;

import java.time.Instant;
import java.util.UUID;

public interface RequestLogCommandPort {
    long countClientRequestsSince(UUID clientId, Instant since);

    void save(RequestLog log);
}
