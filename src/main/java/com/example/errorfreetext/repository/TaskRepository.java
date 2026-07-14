package com.example.errorfreetext.repository;

import com.example.errorfreetext.model.Task;
import com.example.errorfreetext.model.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface TaskRepository extends JpaRepository<Task, UUID> {
    List<Task> findByStatus(TaskStatus status);
}
