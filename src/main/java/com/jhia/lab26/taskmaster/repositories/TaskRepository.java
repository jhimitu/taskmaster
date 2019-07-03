package com.jhia.lab26.taskmaster.repositories;

import com.jhia.lab26.taskmaster.models.Task;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface TaskRepository extends CrudRepository<Task, String> {
    Optional<Task> findById(String id);
}
