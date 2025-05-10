package com.example.repository;

import com.example.entity.ClientAppEntity;
import com.example.entity.RequestLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.util.UUID;

public interface RequestLogRepository extends JpaRepository<RequestLogEntity, UUID> {

    @Query("""
        SELECT COUNT(r)
        FROM RequestLogEntity r
        WHERE r.client = :client AND r.timestamp > :after
    """)
    long countRecentRequests(
            @Param("client") ClientAppEntity client,
            @Param("after") Instant after
    );
}
