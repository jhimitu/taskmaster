package com.jhia.lab26.taskmaster.controllers;

import com.jhia.lab26.taskmaster.models.Task;
import com.jhia.lab26.taskmaster.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
public class TaskController {
    @Autowired
    TaskRepository taskRepository;

    @GetMapping("/")
    public String getHello() {
        return "welcome to Task Master";
    }

    @GetMapping("/tasks")
    public ResponseEntity<Task> getAllTasks() {
        Iterable<Task> tasks = taskRepository.findAll();
        System.out.println(tasks);
        return new ResponseEntity(tasks, HttpStatus.OK);
    }

    @PostMapping("/tasks")
    public ResponseEntity<Task> addTask(
        String title,
        String description
    ) {
        Task task = new Task(title, description, "available");
        taskRepository.save(task);
        return new ResponseEntity(task, HttpStatus.OK);
    }

    @PatchMapping("/tasks/{id}/status")
    public Task patchStatus(@PathVariable UUID id, String status) {
        Task task = taskRepository.findById(id).get();
        task.toggleCompletedStatus(status);
        taskRepository.save(task);
        return task;
    }
}
