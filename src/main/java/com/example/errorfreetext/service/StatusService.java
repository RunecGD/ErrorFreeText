package com.example.errorfreetext.service;

import com.example.errorfreetext.dto.StatusResponse;

/**
 * Business layer (слой бизнес-логики).
 * Описывает бизнес-операции приложения.
 */
public interface StatusService {

    StatusResponse getStatus();
}
