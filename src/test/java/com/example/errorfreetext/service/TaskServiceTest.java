package com.example.errorfreetext.service;

import com.example.errorfreetext.dto.TaskRequest;
import com.example.errorfreetext.dto.TaskResponse;
import com.example.errorfreetext.model.Task;
import com.example.errorfreetext.model.TaskStatus;
import com.example.errorfreetext.repository.TaskRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskService taskService;

    @Test
    public void testCreateTask() {
        TaskRequest request = new TaskRequest();
        request.setText("Hello world");
        request.setLanguage("EN");

        UUID id = UUID.randomUUID();
        Task savedTask = Task.builder().id(id).status(TaskStatus.NEW).build();

        when(taskRepository.save(any(Task.class))).thenReturn(savedTask);

        TaskResponse result = taskService.createTask(request);

        assertEquals(id, result.getId());
    }
}
