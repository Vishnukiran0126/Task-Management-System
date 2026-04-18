package com.example.Task.service;

import com.example.Task.Dtos.CommentMapper;
import com.example.Task.Dtos.CommentRequestDto;
import com.example.Task.Dtos.CommentResponseDto;
import com.example.Task.Exception.ResourceNotFoundException;
import com.example.Task.entites.Comment;
import com.example.Task.entites.Task;
import com.example.Task.entites.User;
import com.example.Task.repositories.CommentRepository;
import com.example.Task.repositories.TaskRepository;
import com.example.Task.repositories.UserRepository;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
public class CommentService {
    private CommentRepository commentRepository;
    private UserRepository ur;
    private TaskRepository tr;
    private CommentMapper cm;

    public CommentService(CommentRepository commentRepository,UserRepository ur,TaskRepository tr,CommentMapper cm) {
        this.commentRepository = commentRepository;
        this.ur=ur;
        this.tr=tr;
        this.cm=cm;
    }

    public List<CommentResponseDto> getCommentsByTaskId(Long taskId) {
        //always validate first if task exists
        // Step 1: Check if task exists
        Task task = tr.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + taskId));

        // Step 2: Fetch comments
        List<Comment> comments = commentRepository.getCommentsByTaskId(taskId);

        // Step 3: Map to DTO
        return comments.stream()
                .map((cmt)->cm.toDto(cmt))
                .toList();
        //return commentRepository.getCommentsByTaskId(taskId).stream().map((cmt)->cm.toDto(cmt)).toList();
    }

    public CommentResponseDto createComment(@RequestBody CommentRequestDto comment) {
        User user =ur.findById(comment.getUserId()).orElseThrow(()-> new ResourceNotFoundException("User with Id not found: "+comment.getUserId()));
        Task task=tr.findById(comment.getTaskId()).orElseThrow(()-> new ResourceNotFoundException("Task with id not found: "+comment.getTaskId()));
        Comment cmt=cm.toEntity(comment,user,task);
        commentRepository.save(cmt);
        return cm.toDto(cmt);
    }

    public CommentResponseDto updateComment(Long commentId, CommentRequestDto comment) {
        //existing comment
        Comment cmt=commentRepository.findById(commentId).orElseThrow(()->new ResourceNotFoundException("Comment not found"));
        if(comment.getMessage()!=null){
            cmt.setMessage(comment.getMessage());
        }
        if(comment.getCreatedDate()!=null){
            cmt.setCreatedDate(comment.getCreatedDate());
        }
        if(comment.getTaskId()!=null){
            cmt.setTask(tr.findById(comment.getTaskId()).orElseThrow(()->new ResourceNotFoundException("Not found task with id: "+comment.getTaskId())));
        }
        if(comment.getUserId()!=null){
            cmt.setUser(ur.findById(comment.getUserId()).orElseThrow(()-> new ResourceNotFoundException("Not found user iwth id: "+comment.getUserId())));
        }
        commentRepository.save(cmt);
        return cm.toDto(cmt);
    }

    public void  deleteComment(Long id) {
        commentRepository.deleteById(id);
    }
}
