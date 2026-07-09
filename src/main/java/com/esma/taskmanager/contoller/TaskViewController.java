package com.esma.taskmanager.contoller;

import com.esma.taskmanager.dto.TaskRequest;
import com.esma.taskmanager.entity.TaskStatus;
import com.esma.taskmanager.service.CategoryService;
import com.esma.taskmanager.service.TaskService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
        model.addAttribute("offenTasks", taskService.getTasksByStatus(TaskStatus.OFFEN));
        model.addAttribute("inBearbeitungTasks", taskService.getTasksByStatus(TaskStatus.IN_BEARBEITUNG));
        model.addAttribute("deployedTasks", taskService.getTasksByStatus(TaskStatus.DEPLOYED));
        model.addAttribute("fertigTasks", taskService.getTasksByStatus(TaskStatus.FERTIG));

        return "tasks";
    }

    @GetMapping("/create")
    public String createTaskPage(Model model){

        model.addAttribute(
                "newTask",
                new TaskRequest(
                        "",
                        "",
                        TaskStatus.OFFEN,
                        null
                )
        );
        model.addAttribute("categories", categoryService.findAll());

        return "create-task";
    }

    @PostMapping("/create")
    public String createTask(
            @ModelAttribute TaskRequest taskRequest){

        taskService.createTask(taskRequest);

        return "redirect:/tasks";
    }

    @PostMapping("/{id}/status")
    public String updateStatus(
            @PathVariable Long id,
            @RequestParam TaskStatus status){

        taskService.updateTaskStatus(id, status);

        return "redirect:/tasks";
    }

}
