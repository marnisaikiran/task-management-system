package com.taskmanager.repository;

import com.taskmanager.entity.TaskColumn;
import com.taskmanager.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TaskColumnRepository extends JpaRepository<TaskColumn, Long> {
    List<TaskColumn> findByBoardOrderByPositionAsc(Board board);
    List<TaskColumn> findByBoardIdOrderByPositionAsc(Long boardId);
}
