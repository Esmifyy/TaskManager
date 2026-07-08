package com.esma.taskmanager.service;


import com.esma.taskmanager.dto.TaskRequest;
import com.esma.taskmanager.dto.TaskResponse;
import com.esma.taskmanager.entity.Task;
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

    public Page<Task> searchTasksByNameAndCompletion(String name, boolean completed, Pageable pageable){
        return taskRepository.findByNameContainingAndCompleted(name, completed, pageable);
    }

    public Page<Task> getTasksByCompletion(boolean status, Pageable pageable){
        return taskRepository.findTasksByCompletionStatus(status, pageable);
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

    public List<TaskResponse> getTasksByCompletionStatus(boolean status){
        final List<Task> completedTasks = taskRepository.findByCompleted(status);
        return completedTasks.stream()
                .map(taskMapper::toResponse)
                .toList();
    }

    public Page<TaskResponse> getTasksByCompletionStatus(boolean status,
                                                         Pageable pageable){
        final Page<Task> completedTasks = taskRepository.findByCompleted(status, pageable);
        return completedTasks.map(taskMapper::toResponse);
    }

    public List<Task> getSearchTasksByName(String name){
        return taskRepository.findByNameContainingIgnoreCase(name);
    }

}
