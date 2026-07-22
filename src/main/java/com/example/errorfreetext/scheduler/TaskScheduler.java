package com.example.errorfreetext.scheduler;

import com.example.errorfreetext.model.Task;
import com.example.errorfreetext.model.TaskStatus;
import com.example.errorfreetext.repository.TaskRepository;
import com.example.errorfreetext.service.SpellerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

import com.example.errorfreetext.service.TaskStatusManager;
import org.springframework.data.domain.PageRequest;
import org.springframework.beans.factory.annotation.Value;

@Slf4j
@Component
@RequiredArgsConstructor
public class TaskScheduler {
    private final TaskRepository taskRepository;
    private final SpellerService spellerService;
    private final TaskStatusManager statusManager;

    @Value("${app.scheduler.batch-size:100}")
    private int batchSize;

    @Scheduled(fixedRateString = "${app.scheduler.rate-ms}")
    public void processNewTasks() {
        List<Task> newTasks = taskRepository.findByStatus(TaskStatus.NEW, PageRequest.of(0, batchSize));

        if (newTasks.isEmpty()) {
            return;
        }

        log.info("Processing batch of {} tasks", newTasks.size());

        for (Task task : newTasks) {
            UUID id = task.getId();
            try {
                statusManager.markAsInProgress(id);

                String corrected = spellerService.correctText(task.getOriginalText(), task.getLanguage());

                statusManager.markAsCompleted(id, corrected);
                log.debug("Task {} marked as COMPLETED", id);
            } catch (Exception e) {
                log.error("Failed to process task {}: {}", id, e.getMessage());
                try {
                    statusManager.markAsFailed(id, e.getMessage());
                } catch (Exception dbEx) {
                    log.error("Critical: Could not save ERROR status for task {}", id, dbEx);
                }
            }
        }
    }
}
