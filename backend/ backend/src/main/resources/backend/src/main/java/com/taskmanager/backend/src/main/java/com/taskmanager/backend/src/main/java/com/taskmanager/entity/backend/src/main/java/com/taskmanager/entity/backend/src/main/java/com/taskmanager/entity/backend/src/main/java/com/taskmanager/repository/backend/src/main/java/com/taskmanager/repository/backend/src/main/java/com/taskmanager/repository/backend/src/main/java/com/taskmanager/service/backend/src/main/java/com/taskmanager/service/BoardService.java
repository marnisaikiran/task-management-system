package com.taskmanager.service;

import com.taskmanager.entity.Board;
import com.taskmanager.entity.TaskColumn;
import com.taskmanager.entity.User;
import com.taskmanager.repository.BoardRepository;
import com.taskmanager.repository.TaskColumnRepository;
import com.taskmanager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class BoardService {
    
    @Autowired
    private BoardRepository boardRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private TaskColumnRepository taskColumnRepository;
    
    public List<Board> getAllBoards() {
        return boardRepository.findAll();
    }
    
    public List<Board> getBoardsByUserId(Long userId) {
        return boardRepository.findByOwnerIdOrderByCreatedAtDesc(userId);
    }
    
    public Optional<Board> getBoardById(Long id) {
        return boardRepository.findById(id);
    }
    
    public Board createBoard(Board board, Long ownerId) {
        User owner = userRepository.findById(ownerId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        board.setOwner(owner);
        Board savedBoard = boardRepository.save(board);
        
        createDefaultColumns(savedBoard);
        
        return savedBoard;
    }
    
    private void createDefaultColumns(Board board) {
        List<String> defaultColumnNames = Arrays.asList("To Do", "In Progress", "Done");
        
        for (int i = 0; i < defaultColumnNames.size(); i++) {
            TaskColumn column = new TaskColumn(defaultColumnNames.get(i), i, board);
            taskColumnRepository.save(column);
        }
    }
    
    public Board updateBoard(Long id, Board boardDetails) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Board not found"));
        
        board.setTitle(boardDetails.getTitle());
        board.setDescription(boardDetails.getDescription());
        
        return boardRepository.save(board);
    }
    
    public void deleteBoard(Long id) {
        boardRepository.deleteById(id);
    }
}
