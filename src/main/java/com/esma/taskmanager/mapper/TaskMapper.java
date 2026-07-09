package com.esma.taskmanager.mapper;

import com.esma.taskmanager.dto.TaskRequest;
import com.esma.taskmanager.dto.TaskResponse;
import com.esma.taskmanager.entity.Task;
import com.esma.taskmanager.entity.TaskStatus;
import org.springframework.stereotype.Component;

@Component
public class TaskMapper {

    public Task toEntity(TaskRequest request){
        return Task.builder()
                .name(request.name())
                .description(request.description())
                .status(request.status() != null ? request.status() : TaskStatus.OFFEN)
                .category(request.category())
                .priority(request.priority() != null ? request.priority() : 2)
                .build();
    }

    public TaskResponse toResponse(Task task){
        return TaskResponse.builder()
                .id(task.getId())
                .name(task.getName())
                .description(task.getDescription())
                .status(task.getStatus())
                .category(task.getCategory())
                .priority(task.getPriority())
                .createdAt(task.getCreatedAt())
                .build();
    }

    public void updateEntityFromRequest(Task task, TaskRequest request){
        task.setName(request.name());
        task.setDescription(request.description());
        task.setStatus(request.status() != null ? request.status() : TaskStatus.OFFEN);
        task.setCategory(request.category());
        task.setPriority(request.priority() != null ? request.priority() : 2);
    }
}
