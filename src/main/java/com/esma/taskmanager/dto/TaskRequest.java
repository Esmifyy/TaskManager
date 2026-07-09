package com.esma.taskmanager.dto;

import com.esma.taskmanager.entity.TaskCategory;
import com.esma.taskmanager.entity.TaskStatus;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record TaskRequest(
        @NotBlank(message = "Name is mandatory")
        @Size(min = 3, max = 100, message = "Name must be between 3 and 100 characters")
        String name,

        @Size(min = 3, max = 500, message = "Description must be less than 500 characters")
        String description,
        TaskStatus status,
        TaskCategory category,

        @Min(value = 1, message = "Priority must be between 1 and 3")
        @Max(value = 3, message = "Priority must be between 1 and 3")
        Integer priority
) {
}
