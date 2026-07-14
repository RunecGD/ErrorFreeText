package com.example.errorfreetext.repository;

/**
 * Data access layer (слой доступа к данным).
 * Отвечает за получение/хранение данных (БД, кэш, внешние источники).
 */
public interface StatusRepository {

    String fetchStatus();
}
