package com.example.errorfreetext.controller;

import com.example.errorfreetext.dto.TaskRequest;
import com.example.errorfreetext.dto.TaskResponse;
import com.example.errorfreetext.service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/task")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;

    @PostMapping
    public ResponseEntity<TaskResponse> createTask(@Valid @RequestBody TaskRequest request) {
        UUID taskId = taskService.createTask(request);
        return ResponseEntity.ok(TaskResponse.builder().id(taskId).build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskResponse> getTask(@PathVariable UUID id) {
        return ResponseEntity.ok(taskService.getTaskResponse(id));
    }
}
