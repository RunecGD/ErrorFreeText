package com.example.errorfreetext.service;

import com.example.errorfreetext.model.Task;
import com.example.errorfreetext.model.TaskStatus;
import com.example.errorfreetext.repository.TaskRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TaskStatusManagerTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskStatusService statusManager;

    @Test
    void testMarkAsInProgress() {
        UUID id = UUID.randomUUID();
        Task task = new Task();
        task.setId(id);

        when(taskRepository.findById(id)).thenReturn(Optional.of(task));

        statusManager.markAsInProgress(id);

        verify(taskRepository).saveAndFlush(argThat(t -> t.getStatus() == TaskStatus.IN_PROGRESS));
    }

    @Test
    void testMarkAsFailed() {
        UUID id = UUID.randomUUID();
        Task task = new Task();
        task.setId(id);

        when(taskRepository.findById(id)).thenReturn(Optional.of(task));

        statusManager.markAsFailed(id, "API Error");

        verify(taskRepository).saveAndFlush(argThat(t ->
                t.getStatus() == TaskStatus.ERROR && t.getErrorMessage().equals("API Error")
        ));
    }
}
