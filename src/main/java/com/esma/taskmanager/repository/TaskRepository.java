package com.esma.taskmanager.repository;

import com.esma.taskmanager.entity.Task;
import com.esma.taskmanager.entity.TaskStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    //Custom Query

    // SELECT * FROM tasks WHERE status = :status
    List<Task> findByStatus(TaskStatus status);

    List<Task> findByNameContainingIgnoreCase(String name);

    Page<Task> findByStatus(TaskStatus status, Pageable pageable);
    Page<Task> findByNameContainingIgnoreCase(String name, Pageable pageable);

    Page<Task> findByNameContainingIgnoreCaseAndStatus(String name, TaskStatus status, Pageable pageable);
}
