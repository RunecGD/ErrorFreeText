package com.example.errorfreetext.dto;

import com.example.errorfreetext.model.TaskStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import java.util.UUID;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TaskResponse {
    private UUID id;
    private TaskStatus status;
    private String correctedText;
    private String errorMessage;
}
