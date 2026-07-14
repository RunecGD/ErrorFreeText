package com.example.errorfreetext.service;

import com.example.errorfreetext.dto.TaskRequest;
import com.example.errorfreetext.model.Task;
import com.example.errorfreetext.model.TaskStatus;
import com.example.errorfreetext.repository.TaskRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class TaskService {
    private final TaskRepository taskRepository;

    @Transactional
    public Task createTask(TaskRequest request) {

        Task task = Task.builder()
                .originalText(request.getText())
                .language(request.getLanguage())
                .status(TaskStatus.NEW)
                .build();
        Task saved = taskRepository.save(task);
        log.info("Создана новая задача с ID: {}", saved.getId());

        return saved;
    }
}
