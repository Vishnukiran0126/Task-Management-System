package com.example.Task.Dtos;

import com.example.Task.entites.Task;
import com.example.Task.repositories.UserRepository;
import com.example.Task.service.ProjectService;
import com.example.Task.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TaskMapper {


    private UserService ur;
    private ProjectService pr;

    public TaskMapper(UserService ur, ProjectService pr){
        this.ur=ur;
        this.pr=pr;
    }

    public Task toEntity(TaskRequestDto task){
        Task t =new Task();
        t.setTitle(task.getTitle());
        t.setDescription(task.getDescription());
        t.setPriority(task.getPriority());
        t.setStatus(task.getStatus());
        t.setDueDate(task.getDueDate());
       t.setProject(pr.getProjectById(task.getProjectId()));
        t.setAssignedUser(ur.getUserById(task.getAssignedUser()));
        //t.setComments(task.getComments());

        return t;
    }

    public TaskResponseDto toDto(Task t){
        TaskResponseDto Trd=new TaskResponseDto();
        Trd.setId(t.getId());
        Trd.setDescription(t.getDescription());
        Trd.setPriority(t.getPriority());
        Trd.setDueDate(t.getDueDate());
        Trd.setStatus(t.getStatus());
        Trd.setTitle(t.getTitle());
        Trd.setAssignedUser(t.getAssignedUser().getName());
        Trd.setAssignedUserId(t.getAssignedUser().getId());
        return Trd;
    }
}
