package com.taskmanager.controller;

import com.taskmanager.entity.Board;
import com.taskmanager.entity.TaskColumn;
import com.taskmanager.service.BoardService;
import com.taskmanager.repository.TaskColumnRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/boards")
@CrossOrigin(origins = "*")
public class BoardController {
    
    @Autowired
    private BoardService boardService;
    
    @Autowired
    private TaskColumnRepository taskColumnRepository;
    
    @GetMapping
    public List<Board> getAllBoards() {
        return boardService.getAllBoards();
    }
    
    @GetMapping("/user/{userId}")
    public List<Board> getBoardsByUserId(@PathVariable Long userId) {
        return boardService.getBoardsByUserId(userId);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Board> getBoardById(@PathVariable Long id) {
        return boardService.getBoardById(id)
                .map(board -> ResponseEntity.ok().body(board))
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/{id}/columns")
    public List<TaskColumn> getBoardColumns(@PathVariable Long id) {
        return taskColumnRepository.findByBoardIdOrderByPositionAsc(id);
    }
    
    @PostMapping
    public ResponseEntity<Board> createBoard(@Valid @RequestBody Board board, @RequestParam Long ownerId) {
        try {
            Board createdBoard = boardService.createBoard(board, ownerId);
            return ResponseEntity.ok(createdBoard);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Board> updateBoard(@PathVariable Long id, @Valid @RequestBody Board boardDetails) {
        try {
            Board updatedBoard = boardService.updateBoard(id, boardDetails);
            return ResponseEntity.ok(updatedBoard);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @
