package com.example.errorfreetext.dto;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Builder
public class ErrorResponse {
    private String errorMessage;
    private int errorCode;
    private LocalDateTime timestamp;
    private String path;
}
