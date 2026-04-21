package com.graysenko.FullstackTM.Services;

import com.graysenko.FullstackTM.Entities.Task;
import com.graysenko.FullstackTM.Repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class TaskService {

    private final TaskRepository taskRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    //CRUD

    public Page<Task> findAllTasks(Pageable pageable) {
        return taskRepository.findAll(pageable);
    }

    public Optional<Task> findTaskById(Long id) {
        return taskRepository.findById(id);
    }

    public void save(Task task) {
        taskRepository.save(task);
    }

    public void delete(Task task) {
        taskRepository.delete(task);
    }

    //SEARCH

    public List<Task> findTaskByNameContaining(String name) {
        return taskRepository.findByNameContaining(name);
    }


}
