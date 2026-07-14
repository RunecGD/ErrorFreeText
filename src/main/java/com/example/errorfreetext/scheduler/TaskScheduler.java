package com.example.errorfreetext.scheduler;

import com.example.errorfreetext.model.Task;
import com.example.errorfreetext.model.TaskStatus;
import com.example.errorfreetext.repository.TaskRepository;
import com.example.errorfreetext.service.SpellerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class TaskScheduler {
    private final TaskRepository taskRepository;
    private final SpellerService spellerService;

    @Scheduled(fixedRateString = "${app.scheduler.rate-ms}")
    public void processNewTasks() {
        List<Task> newTasks = taskRepository.findByStatus(TaskStatus.NEW);
        if (newTasks.isEmpty()) return;

        log.info("Found {} new tasks to process", newTasks.size());

        for (Task task : newTasks) {
            processTask(task);
        }
    }

    private void processTask(Task task) {
        try {
            updateStatus(task, TaskStatus.IN_PROGRESS, null);

            String corrected = spellerService.correctText(task.getOriginalText(), task.getLanguage());

            task.setCorrectedText(corrected);
            updateStatus(task, TaskStatus.COMPLETED, null);
            log.info("Task {} completed successfully", task.getId());
        } catch (Exception e) {
            log.error("Failed to process task {}", task.getId(), e);
            updateStatus(task, TaskStatus.ERROR, e.getMessage());
        }
    }

    @Transactional
    protected void updateStatus(Task task, TaskStatus status, String errorMessage) {
        task.setStatus(status);
        task.setErrorMessage(errorMessage);
        taskRepository.save(task);
    }
}
