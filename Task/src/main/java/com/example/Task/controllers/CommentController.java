package com.example.Task.controllers;

import com.example.Task.Dtos.CommentRequestDto;
import com.example.Task.Dtos.CommentResponseDto;
import com.example.Task.entites.Comment;
import com.example.Task.repositories.CommentRepository;
import com.example.Task.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/comments/{taskId}")
    public ResponseEntity<List<CommentResponseDto>> getComments(@PathVariable long taskId) {
        return new ResponseEntity<>(commentService.getCommentsByTaskId(taskId), HttpStatus.OK);
    }

    @PostMapping("/comment")
    public ResponseEntity<CommentResponseDto> createComment(@RequestBody CommentRequestDto comment) {
        return new ResponseEntity<>(commentService.createComment(comment), HttpStatus.CREATED);
    }

    @PutMapping("/comment/{id}")
    public ResponseEntity<CommentResponseDto> updateComment(@PathVariable("id") Long commentId,@RequestBody CommentRequestDto comment){
        return new ResponseEntity<>(commentService.updateComment(commentId,comment), HttpStatus.OK);
    }

    @DeleteMapping("/comments/{id}")
    public ResponseEntity<?> deleteComment(@PathVariable Long id){
       commentService.deleteComment(id);
       return new ResponseEntity<>(HttpStatus.OK);
    }
}
