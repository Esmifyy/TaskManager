package com.esma.taskmanager.dto;


import com.esma.taskmanager.entity.Category;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record TaskResponse(
        Long id,
        String name,
        String description,
        Boolean completed,
        LocalDateTime createdAt,
        CategoryResponse category
) {
}
