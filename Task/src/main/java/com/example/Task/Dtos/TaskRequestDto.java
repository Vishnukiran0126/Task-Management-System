package com.example.Task.Dtos;

import com.example.Task.entites.Comment;
import com.example.Task.enums.TaskPriority;
import com.example.Task.enums.TaskStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TaskRequestDto {

    private String title;
    private String description;
    private TaskStatus status;
    private TaskPriority priority;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate dueDate;
    private Long projectId;
    private Long assignedUser;
    //private List<Comment> comments;
}
