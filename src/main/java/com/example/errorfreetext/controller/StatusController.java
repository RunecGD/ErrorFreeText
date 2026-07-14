package com.example.errorfreetext.controller;

import com.example.errorfreetext.dto.StatusResponse;
import com.example.errorfreetext.service.StatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Presentation layer (слой представления).
 * Отвечает за обработку HTTP-запросов и формирование ответов.
 */
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class StatusController {

    private final StatusService statusService;

    @GetMapping("/status")
    public StatusResponse getStatus() {
        return statusService.getStatus();
    }
}
