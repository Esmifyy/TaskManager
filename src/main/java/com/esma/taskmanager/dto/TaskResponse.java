package com.esma.taskmanager.dto;


import com.esma.taskmanager.entity.Category;
import com.esma.taskmanager.entity.TaskStatus;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record TaskResponse(
        Long id,
        String name,
        String description,
        TaskStatus status,
        LocalDateTime createdAt,
        CategoryResponse category
) {
}
