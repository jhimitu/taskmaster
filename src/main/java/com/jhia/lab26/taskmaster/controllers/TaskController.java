package com.jhia.lab26.taskmaster.controllers;

import com.jhia.lab26.taskmaster.models.Task;
import com.jhia.lab26.taskmaster.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TaskController {
    @Autowired
    TaskRepository taskRepository;

    @GetMapping("/")
    public String getHello() {
        return "welcome to Task Master";
    }

    @GetMapping("/tasks")
    public List<Task> getAllTasks() {
        return (List) taskRepository.findAll();
    }

    @PostMapping("/tasks")
    public void addTask(
        @PathVariable String title,
        @PathVariable String description
    ) {
        Task task = new Task(title, description, "available");
        taskRepository.save(task);
    }

    @PatchMapping("/tasks/{id}/status")
    public Task patchStatus(@PathVariable String id) {
        Task task = taskRepository.findById(id).get();
//        task.toggleCompletedStatus();
        taskRepository.save(task);
        return task;
    }
}
