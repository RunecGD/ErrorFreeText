package com.example.errorfreetext.dto;

import java.time.Instant;

/**
 * DTO ответа для эндпоинта /api/status.
 */
public record StatusResponse(String status, Instant timestamp) {
}
