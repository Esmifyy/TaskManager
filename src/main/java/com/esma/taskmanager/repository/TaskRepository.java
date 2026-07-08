package com.esma.taskmanager.repository;

import com.esma.taskmanager.entity.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    //Custom Query

    // SELECT * FROM tasks WHERE completed = :completed
    List<Task> findByCompleted(boolean completed);

    List<Task> findByNameContainingIgnoreCase(String name);

    @Query("SELECT t FROM Task t WHERE t.completed = :completed")
    List<Task> findTasksByCompletionStatus(@Param("completed") boolean completed);

    Page<Task> findByCompleted(boolean completed, Pageable pageable);
    Page<Task> findByNameContainingIgnoreCase(String name, Pageable pageable);

    @Query("SELECT t FROM Task t WHERE t.completed = :completed")
    Page<Task> findTasksByCompletionStatus(@Param("completed") boolean completed,
                                           Pageable pageable);

    @Query("SELECT t FROM Task t WHERE LOWER(t.name) LIKE LOWER(CONCAT('%', :name, '%')) AND t.completed = :completed")
    Page<Task> findByNameContainingAndCompleted(String name, boolean completed, Pageable pageable);
}
