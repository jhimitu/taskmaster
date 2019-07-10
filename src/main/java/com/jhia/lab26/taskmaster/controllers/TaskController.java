package com.jhia.lab26.taskmaster.controllers;

import com.jhia.lab26.taskmaster.models.Task;
import com.jhia.lab26.taskmaster.repositories.S3Upload;
import com.jhia.lab26.taskmaster.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@CrossOrigin
@RestController
public class TaskController {

    private S3Upload s3Upload;

    @Autowired
    TaskController(S3Upload s3Upload) {
        this.s3Upload = s3Upload;
    }

    @Autowired
    TaskRepository taskRepository;

    @CrossOrigin
    @GetMapping("/")
    public String getHello() {
        return "welcome to Task Master";
    }

    @CrossOrigin
    @GetMapping("/tasks")
    public ResponseEntity<Task> getAllTasks() {
        Iterable<Task> tasks = taskRepository.findAll();
        System.out.println(tasks);
        return new ResponseEntity(tasks, HttpStatus.OK);
    }

    @CrossOrigin
    @PostMapping("/tasks")
    public ResponseEntity<Task> addTask(
        String title,
        String description
    ) {
        Task task = new Task(title, description, "none", "available");
        taskRepository.save(task);
        return new ResponseEntity(task, HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping("/users/{name}/tasks")
    public ResponseEntity<Task> getAssigneeTasks(@PathVariable String name) {
        List<Task> tasks = taskRepository.findAllByAssignee(name);
        return new ResponseEntity(tasks, HttpStatus.OK);
    }

    @CrossOrigin
    @PatchMapping("/tasks/{id}/assign/{assignee}")
    public Task assignTask(@PathVariable UUID id, String assignee) {
        Task task = taskRepository.findById(id).get();
        task.assign(assignee);
        taskRepository.save(task);
        return task;
    }

    @CrossOrigin
    @PatchMapping("/tasks/{id}/status")
    public Task patchStatus(@PathVariable UUID id, String status) {
        Task task = taskRepository.findById(id).get();
        task.toggleCompletedStatus(status);
        taskRepository.save(task);
        return task;
    }

    @PostMapping("/tasks/{id}/images")
    public ResponseEntity uploadImage(
        @PathVariable UUID id,
        @RequestPart(value = "file") MultipartFile file
        ) {

        Task task = taskRepository.findById(id).get();

        String image = this.s3Upload.uploadFile(file);
        task.setImageURL(image);
        taskRepository.save(task);
        return new ResponseEntity(task, HttpStatus.OK);
    }

}
