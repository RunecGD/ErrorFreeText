package com.example.errorfreetext.service;

import com.example.errorfreetext.model.TaskStatus;
import com.example.errorfreetext.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TaskStatusService {
    private final TaskRepository taskRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void markAsInProgress(UUID taskId) {
        updateStatus(taskId, TaskStatus.IN_PROGRESS, null, null);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void markAsCompleted(UUID taskId, String correctedText) {
        updateStatus(taskId, TaskStatus.COMPLETED, correctedText, null);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void markAsFailed(UUID taskId, String errorMessage) {
        updateStatus(taskId, TaskStatus.ERROR, null, errorMessage);
    }

    private void updateStatus(UUID taskId, TaskStatus status, String text, String error) {
        taskRepository.findById(taskId).ifPresent(task -> {
            task.setStatus(status);
            if (text != null) task.setCorrectedText(text);
            if (error != null) task.setErrorMessage(error);
            taskRepository.saveAndFlush(task);
        });
    }
}
