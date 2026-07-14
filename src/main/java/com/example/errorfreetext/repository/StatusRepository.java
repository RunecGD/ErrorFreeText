package com.example.errorfreetext.repository;

import org.springframework.stereotype.Repository;

/**
 * Простейшая реализация слоя доступа к данным.
 * Здесь в дальнейшем можно подключить БД (PostgreSQL уже есть в зависимостях).
 */
@Repository
public class StatusRepository {

    public String fetchStatus() {
        return "UP";
    }
}
