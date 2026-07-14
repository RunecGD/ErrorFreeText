package com.example.errorfreetext.repository.impl;

import com.example.errorfreetext.repository.StatusRepository;
import org.springframework.stereotype.Repository;

/**
 * Простейшая реализация слоя доступа к данным.
 * Здесь в дальнейшем можно подключить БД (PostgreSQL уже есть в зависимостях).
 */
@Repository
public class StatusRepositoryImpl implements StatusRepository {

    @Override
    public String fetchStatus() {
        return "UP";
    }
}
