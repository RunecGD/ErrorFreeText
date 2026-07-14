package com.example.errorfreetext.controller;

import com.example.errorfreetext.dto.TaskRequest;
import com.example.errorfreetext.dto.TaskResponse;
import com.example.errorfreetext.model.Task;
import com.example.errorfreetext.service.TaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/task")
@RequiredArgsConstructor
@Slf4j
public class TaskController {
    private final TaskService taskService;
    @PostMapping
    public ResponseEntity<TaskResponse> createTask( @RequestBody TaskRequest request) {
        log.debug("Запрос на создание задачи: {}", request);

        Task saved = taskService.createTask(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new TaskResponse(saved.getId()));
    }

}
