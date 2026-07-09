package com.esma.taskmanager.mapper;

import com.esma.taskmanager.dto.CategoryResponse;
import com.esma.taskmanager.dto.TaskRequest;
import com.esma.taskmanager.dto.TaskResponse;
import com.esma.taskmanager.entity.Category;
import com.esma.taskmanager.entity.Task;
import com.esma.taskmanager.entity.TaskStatus;
import com.esma.taskmanager.service.CategoryService;
import org.springframework.stereotype.Component;

@Component
public class TaskMapper {

    private final CategoryService categoryService;

    public TaskMapper(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    public Task toEntity(TaskRequest request){

        Category category = null;

        if(request != null && request.categoryId() != null){
            category = categoryService.findById(request.categoryId());
        }

        return Task.builder()
                .name(request.name())
                .description(request.description())
                .status(request.status() != null ? request.status() : TaskStatus.OFFEN)
                .category(category)
                .build();
    }

    public TaskResponse toResponse(Task task){

        CategoryResponse categoryResponse = null;

        if(task != null && task.getCategory() != null){
            categoryResponse = CategoryResponse.builder()
                    .categoryId(task.getCategory().getId())
                    .name(task.getCategory().getName())
                    .description(task.getCategory().getDescription())
                    .build();
        }

        return TaskResponse.builder()
                .id(task.getId())
                .name(task.getName())
                .description(task.getDescription())
                .status(task.getStatus())
                .createdAt(task.getCreatedAt())
                .category(categoryResponse)
                .build();
    }

    public void updateEntityFromRequest(Task task, TaskRequest request){
        task.setName(request.name());
        task.setDescription(request.description());
        task.setStatus(request.status() != null ? request.status() : TaskStatus.OFFEN);

        if (request.categoryId() != null){
            task.setCategory(categoryService.findById(request.categoryId()));
        }
    }
}
