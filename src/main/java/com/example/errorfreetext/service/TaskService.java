package com.example.errorfreetext.service;

import com.example.errorfreetext.dto.TaskRequest;
import com.example.errorfreetext.dto.TaskResponse;
import com.example.errorfreetext.exception.TaskNotFoundException;
import com.example.errorfreetext.model.Task;
import com.example.errorfreetext.model.TaskStatus;
import com.example.errorfreetext.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;

    @Transactional
    public TaskResponse createTask(TaskRequest request) {
        Task task = Task.builder()
                .originalText(request.getText())
                .language(request.getLanguage())
                .status(TaskStatus.NEW)
                .build();
        return TaskResponse.builder()
                .id(taskRepository.save(task).getId())
                .build();
    }

    public TaskResponse getTaskResponse(UUID id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("Task with id: " + id + " not found"));

        TaskResponse.TaskResponseBuilder builder = TaskResponse.builder()
                .status(task.getStatus());

        if (task.getStatus() == TaskStatus.COMPLETED) {
            builder.correctedText(task.getCorrectedText());
        } else if (task.getStatus() == TaskStatus.ERROR) {
            builder.errorMessage(task.getErrorMessage());
        }

        return builder.build();
    }
}
