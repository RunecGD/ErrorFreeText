package com.example.errorfreetext.service;

import com.example.errorfreetext.dto.StatusResponse;
import com.example.errorfreetext.repository.StatusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;

/**
 * Реализация бизнес-логики.
 * Использует слой доступа к данным (repository).
 */
@Service
@RequiredArgsConstructor
public class StatusService {

    private final StatusRepository statusRepository;

    public StatusResponse getStatus() {
        String status = statusRepository.fetchStatus();
        return new StatusResponse(status, Instant.now());
    }
}
