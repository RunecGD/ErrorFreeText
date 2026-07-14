package com.example.errorfreetext.service.impl;

import com.example.errorfreetext.dto.StatusResponse;
import com.example.errorfreetext.repository.StatusRepository;
import com.example.errorfreetext.service.StatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;

/**
 * Реализация бизнес-логики.
 * Использует слой доступа к данным (repository).
 */
@Service
@RequiredArgsConstructor
public class StatusServiceImpl implements StatusService {

    private final StatusRepository statusRepository;

    @Override
    public StatusResponse getStatus() {
        String status = statusRepository.fetchStatus();
        return new StatusResponse(status, Instant.now());
    }
}
