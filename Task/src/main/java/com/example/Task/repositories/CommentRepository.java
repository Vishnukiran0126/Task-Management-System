package com.example.Task.repositories;

import com.example.Task.entites.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment,Long> {
    public List<Comment> getCommentsByTaskId(Long taskId);
}
