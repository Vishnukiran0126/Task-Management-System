package com.example.Task.Dtos;

import com.example.Task.entites.Project;
import com.example.Task.entites.User;
import org.springframework.stereotype.Component;

@Component
public class ProjectMapper {

    public Project toEntity(ProjectRequestDto dto, User user){
        Project pr=new Project();
        pr.setName(dto.getName());
        pr.setDescription(dto.getDescription());
        pr.setCreatedBy(user); // this one was fetched from service and sent here

        return pr;

    }

    public ProjectResponseDto toDto(Project pr){
        ProjectResponseDto dto =new ProjectResponseDto();
        dto.setProjectId(pr.getId());
        dto.setName(pr.getName());
        dto.setDescription(pr.getDescription());
        dto.setCreatedByName(pr.getCreatedBy().getName());
        dto.setCreatedById(pr.getCreatedBy().getId());
        return dto;
    }

}
