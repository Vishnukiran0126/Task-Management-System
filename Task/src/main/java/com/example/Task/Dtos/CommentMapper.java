package com.example.Task.Dtos;

import com.example.Task.entites.Comment;
import com.example.Task.entites.Task;
import com.example.Task.entites.User;
import org.springframework.stereotype.Component;

@Component
public class CommentMapper {

    public Comment toEntity(CommentRequestDto dto, User user, Task task){
        Comment comment=new Comment();
        comment.setMessage(dto.getMessage());
        comment.setCreatedDate(dto.getCreatedDate());
        comment.setUser(user);
        comment.setTask(task);

        return comment;
    }

    public CommentResponseDto toDto(Comment comment){
        CommentResponseDto dto=new CommentResponseDto();
        dto.setUserId(comment.getUser().getId());
        dto.setTaskId(comment.getTask().getId());
        dto.setMessage(comment.getMessage());
        dto.setUserName(comment.getUser().getName());
        dto.setId(comment.getId());
        dto.setCreatedDate(comment.getCreatedDate());
        return dto;
    }
}
