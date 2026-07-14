package com.example.errorfreetext.dto;

import com.example.errorfreetext.model.TaskStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskResponse {
    private UUID id;
    private TaskStatus status;
    private String correctedText;
    private String errorMessage;

    public TaskResponse(UUID id) {
        this.id = id;
    }

}