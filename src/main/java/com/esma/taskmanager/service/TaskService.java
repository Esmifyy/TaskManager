package com.esma.taskmanager.service;


import com.esma.taskmanager.dto.TaskRequest;
import com.esma.taskmanager.dto.TaskResponse;
import com.esma.taskmanager.entity.Task;
import com.esma.taskmanager.entity.TaskStatus;
import com.esma.taskmanager.exception.TaskNotFoundException;
import com.esma.taskmanager.mapper.TaskMapper;
import com.esma.taskmanager.repository.TaskRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class TaskService {

    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    public TaskService(TaskRepository taskRepository, TaskMapper taskMapper) {
        this.taskRepository = taskRepository;
        this.taskMapper = taskMapper;
    }

    public List<Task> getAllTasks(){
        return taskRepository.findAll();
    }

    public Page<Task> getAllTasks(Pageable pageable){
        return taskRepository.findAll(pageable);
    }

    public TaskResponse getTaskById(Long id){
        Task retriveTask = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));
        return taskMapper.toResponse(retriveTask);
    }

    public Page<Task> searchTasksByName(String name, Pageable pageable){
        return taskRepository.findByNameContainingIgnoreCase(name, pageable);
    }

    public Page<Task> searchTasksByNameAndStatus(String name, TaskStatus status, Pageable pageable){
        return taskRepository.findByNameContainingIgnoreCaseAndStatus(name, status, pageable);
    }

    public Page<Task> getTasksByStatus(TaskStatus status, Pageable pageable){
        return taskRepository.findByStatus(status, pageable);
    }

    public TaskResponse createTask(TaskRequest task){
        Task entityTask = taskMapper.toEntity(task);
        Task savedTask = taskRepository.save(entityTask);
        return taskMapper.toResponse(savedTask);
    }

    public TaskResponse updateTask(Long id, TaskRequest updatedTask){
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));

        taskMapper.updateEntityFromRequest(task, updatedTask);
        taskRepository.save(task);

        return taskMapper.toResponse(taskRepository.save(task));
    }

    public void deleteTaskById(Long id){
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));
        taskRepository.delete(task);
    }

    public List<TaskResponse> getTasksByStatus(TaskStatus status){
        final List<Task> tasks = taskRepository.findByStatus(status);
        return tasks.stream()
                .map(taskMapper::toResponse)
                .toList();
    }

    public TaskResponse updateTaskStatus(Long id, TaskStatus status){
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));
        task.setStatus(status);
        return taskMapper.toResponse(taskRepository.save(task));
    }

    public List<Task> getSearchTasksByName(String name){
        return taskRepository.findByNameContainingIgnoreCase(name);
    }

}
