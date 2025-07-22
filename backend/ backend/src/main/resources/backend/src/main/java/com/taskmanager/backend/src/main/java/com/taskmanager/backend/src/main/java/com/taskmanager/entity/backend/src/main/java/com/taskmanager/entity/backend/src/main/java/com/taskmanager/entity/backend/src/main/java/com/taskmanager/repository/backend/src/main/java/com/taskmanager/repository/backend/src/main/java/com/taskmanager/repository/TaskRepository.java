package com.taskmanager.repository;

import com.taskmanager.entity.Task;
import com.taskmanager.entity.TaskColumn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByColumnOrderByPositionAsc(TaskColumn column);
    List<Task> findByColumnIdOrderByPositionAsc(Long columnId);
    List<Task> findByAssignedToId(Long userId);
}
