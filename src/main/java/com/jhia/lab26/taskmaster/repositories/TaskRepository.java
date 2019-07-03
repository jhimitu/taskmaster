package com.jhia.lab26.taskmaster.repositories;

import com.jhia.lab26.taskmaster.models.Task;
import org.springframework.data.repository.CrudRepository;

public interface TaskRepository extends CrudRepository<Task, String> {
}
