package com.graysenko.FullstackTM.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.graysenko.FullstackTM.Entities.Task;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByNameContaining(String name);
}
