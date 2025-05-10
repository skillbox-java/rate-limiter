package com.example;

import java.time.Instant;
import java.util.UUID;

public record RequestLog(
        UUID id,
        UUID clientId,
        Instant timestamp
) {}
