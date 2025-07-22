package com.taskmanager.service;

import com.taskmanager.entity.Task;
import com.taskmanager.entity.TaskColumn;
import com.taskmanager.repository.TaskRepository;
import com.taskmanager.repository.TaskColumnRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService {
    
    @Autowired
    private TaskRepository taskRepository;
    
    @Autowired
    private TaskColumnRepository taskColumnRepository;
    
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }
    
    public List<Task> getTasksByColumnId(Long columnId) {
        return taskRepository.findByColumnIdOrderByPositionAsc(columnId);
    }
    
    public Optional<Task> getTaskById(Long id) {
        return taskRepository.findById(id);
    }
    
    public Task createTask(Task task, Long columnId) {
        TaskColumn column = taskColumnRepository.findById(columnId)
                .orElseThrow(() -> new RuntimeException("Column not found"));
        
        task.setColumn(column);
        
        List<Task> existingTasks = taskRepository.findByColumnIdOrderByPositionAsc(columnId);
        task.setPosition(existingTasks.size());
        
        return taskRepository.save(task);
    }
    
    public Task updateTask(Long id, Task taskDetails) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found"));
        
        task.setTitle(taskDetails.getTitle());
        task.setDescription(taskDetails.getDescription());
        task.setPriority(taskDetails.getPriority());
        task.setDueDate(taskDetails.getDueDate());
        
        return taskRepository.save(task);
    }
    
    public Task moveTask(Long taskId, Long newColumnId, Integer newPosition) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));
        
        TaskColumn newColumn = taskColumnRepository.findById(newColumnId)
                .orElseThrow(() -> new RuntimeException("Column not found"));
        
        task.setColumn(newColumn);
        task.setPosition(newPosition);
        
        return taskRepository.save(task);
    }
    
    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }
}
