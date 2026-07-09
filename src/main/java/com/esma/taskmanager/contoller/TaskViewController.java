package com.esma.taskmanager.contoller;

import com.esma.taskmanager.dto.TaskRequest;
import com.esma.taskmanager.entity.Category;
import com.esma.taskmanager.service.CategoryService;
import com.esma.taskmanager.service.TaskService;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/tasks")
public class TaskViewController {

    private final TaskService taskService;
    private final CategoryService categoryService;

    public TaskViewController(TaskService taskService,  CategoryService categoryService) {
        this.taskService = taskService;
        this.categoryService = categoryService;
    }

    @GetMapping
    public String showTasks(Model model){
        model.addAttribute("tasks", taskService.getAllTasks(PageRequest.of(0, 100)).getContent());

        model.addAttribute(
                "newTask",
                new TaskRequest(
                        "",
                        "",
                        false,
                        null
                )
        );

        return "tasks";
    }

    @GetMapping("/create")
    public String createTaskPage(Model model){

        model.addAttribute(
                "newTask",
                new TaskRequest(
                        "",
                        "",
                        false,
                        null
                )
        );

        return "create-task";
    }

    @PostMapping("/create")
    public String createTask(
            @ModelAttribute TaskRequest taskRequest){

        taskService.createTask(taskRequest);

        return "redirect:/tasks";
    }

}
