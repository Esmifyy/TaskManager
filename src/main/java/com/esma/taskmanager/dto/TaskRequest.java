package com.esma.taskmanager.dto;

import com.esma.taskmanager.entity.TaskStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record TaskRequest(
        @NotBlank(message = "Name is mandatory")
        @Size(min = 3, max = 100, message = "Name must be between 3 and 100 characters")
        String name,

        @Size(min = 3, max = 500, message = "Description must be less than 500 characters")
        String description,
        TaskStatus status,
        Long categoryId
) {
}
