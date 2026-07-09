package com.esma.taskmanager.contoller;

import com.esma.taskmanager.dto.TaskRequest;
import com.esma.taskmanager.dto.TaskResponse;
import com.esma.taskmanager.entity.Task;
import com.esma.taskmanager.entity.TaskStatus;
import com.esma.taskmanager.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController // @Controller and @ResponseBody in RestController zusammengefasst
@RequestMapping("api/v/tasks") //localhost:8080/api/v/tasks
public class TaskController {

    private final TaskService taskService;

    //Constructor Injection
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/search")
    public ResponseEntity<Map<String, Object>> searchTasks(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) TaskStatus status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "DESC") String sortDir
    ){
        Sort sort = sortDir.equalsIgnoreCase("ASC")
                ? Sort.by(sortBy).ascending() :
                Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Task> taskPage;

        if(name != null && status != null){
            // Filter by both
            taskPage = taskService.searchTasksByNameAndStatus(
                    name, status, pageable
            );
        }else if(name != null){
            //Filter by name only
            taskPage = taskService.searchTasksByName(name, pageable);
        }else if(status != null){
            //Filter by status only
            taskPage = taskService.getTasksByStatus(status, pageable);
        }else{
            taskPage = taskService.getAllTasks(pageable);
        }

        List<TaskResponse> tasks = taskPage.getContent()
                .stream()
                .map(task -> new TaskResponse(
                        task.getId(),
                        task.getName(),
                        task.getDescription(),
                        task.getStatus(),
                        task.getCategory(),
                        task.getPriority(),
                        task.getCreatedAt()))
                .toList();

        Map<String, Object> response = new HashMap<>();
        response.put("tasks", tasks);
        response.put("currentPage", taskPage.getNumber());
        response.put("totalItems", taskPage.getTotalPages());
        response.put("totalPages", taskPage.getTotalElements());
        response.put("hasNext", taskPage.hasNext());
        response.put("hasPrevious", taskPage.hasPrevious());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/getAllTasks")
    public ResponseEntity<Map<String, Object>> getAllTasks(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "DESC") String sortDir
    ){
        Sort sort = sortDir.equalsIgnoreCase("ASC")
                ? Sort.by(sortBy).ascending() :
                Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Task> taskPage = taskService.getAllTasks(pageable);

        List<TaskResponse> tasks = taskPage.getContent()
                .stream()
                .map(task -> new TaskResponse(
                        task.getId(),
                        task.getName(),
                        task.getDescription(),
                        task.getStatus(),
                        task.getCategory(),
                        task.getPriority(),
                        task.getCreatedAt()))
                .toList();

        Map<String, Object> response = new HashMap<>();
        response.put("tasks", tasks);
        response.put("currentPage", taskPage.getNumber());
        response.put("totalItems", taskPage.getTotalPages());
        response.put("totalPages", taskPage.getTotalElements());
        response.put("hasNext", taskPage.hasNext());
        response.put("hasPrevious", taskPage.hasPrevious());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

//    @GetMapping
//    public List<Task> getAllTaks(){
//        return taskService.getAllTasks();
//    }

    @GetMapping("/{id}")
    public TaskResponse getTaskById(@PathVariable Long id){
        return taskService.getTaskById(id);
    }

    @GetMapping("/status/{status}")
    public List<TaskResponse> getTasksByStatus(@PathVariable TaskStatus status){
        return taskService.getTasksByStatus(status);
    }

    @GetMapping("/search-by-Name")
    public List<Task> searchTasksByName(@RequestParam String name){
        return taskService.getSearchTasksByName(name);
    }

    @PostMapping("/create")
    public ResponseEntity<TaskResponse> createTask(@Valid @RequestBody TaskRequest task){
        TaskResponse savedTask = taskService.createTask(task);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedTask);
    }

    @PutMapping("/{id}")
    public TaskResponse updateTask(@PathVariable Long id, @Valid @RequestBody TaskRequest updateTask){
        return taskService.updateTask(id, updateTask);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id){
        taskService.deleteTaskById(id);
        return ResponseEntity.ok().build();
    }

    /**
     * 200 OK          - Successful GET, PUT, DELETE
     * 201 Created     - Successful POST
     * 404 Not Found   - Resource doesnt exist
     * 400 Bad Request - Invalid Data
     */

}
