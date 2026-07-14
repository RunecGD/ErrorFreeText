package com.example.errorfreetext.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class TaskRequest {
    @NotBlank(message = "Text cannot be blank")
    @Size(min = 3, message = "Text must be at least 3 characters long")
    @Pattern(regexp = ".*[a-zA-Zа-яА-Я].*", message = "Text must contain at least one letter")
    private String text;

    @NotBlank(message = "Language is required")
    @Pattern(regexp = "^(EN|RU)$", message = "Language must be EN or RU")
    private String language;
}
