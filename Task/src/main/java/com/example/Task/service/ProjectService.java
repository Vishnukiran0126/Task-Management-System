package com.example.Task.service;

import com.example.Task.Dtos.Pagination.PaginatedResponse;
import com.example.Task.Dtos.Pagination.PaginationRequest;
import com.example.Task.Dtos.ProjectMapper;
import com.example.Task.Dtos.ProjectRequestDto;
import com.example.Task.Dtos.ProjectResponseDto;
import com.example.Task.Exception.ResourceNotFoundException;
import com.example.Task.Specification.ProjectSpecification;
import com.example.Task.entites.Project;
import com.example.Task.entites.Task;
import com.example.Task.entites.User;
import com.example.Task.repositories.ProjectRepository;
import com.example.Task.repositories.UserRepository;
import com.example.Task.utils.PaginationUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectService {

    private ProjectRepository projectRepository;
    private ProjectMapper pm;
    private UserRepository um; //user Repository instead of another service


    public ProjectService(ProjectRepository projectRepository,ProjectMapper pm,UserRepository um) {
        this.projectRepository = projectRepository;
        this.pm=pm;
        this.um=um;
    }

    public PaginatedResponse<ProjectResponseDto> getAllProjects(PaginationRequest pr, String createdBy, String projectName) {
        Pageable p= PaginationUtils.getPageable(pr);
        Specification<Project> spec= ProjectSpecification.getAllProjects(projectName,createdBy);
        Page<Project> res=projectRepository.findAll(spec,p);
        PaginatedResponse<ProjectResponseDto> ans=PaginatedResponse.<ProjectResponseDto>builder()
                .size(res.getSize())
                .page(res.getNumber())
                .totalElements(res.getTotalElements())
                .totalPages(res.getTotalPages())
                .content(res.getContent().stream().map(pm::toDto).toList())
                .build();
        return ans;
       // return projectRepository.findAll().stream().map(pm::toDto).toList();
    }

    public Project getProjectById(long id) {
        return projectRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Project not found with id: "+id));
    }

    public Project getProjectByName(String name) {
        return projectRepository.getProjectByName(name);
    }

    public ProjectResponseDto addProject(ProjectRequestDto project) {
//        Project p=new Project();
//        p.setName(project.getName())
//        p.setDescription(project.getDescription());
        User user = um.findById(project.getCreatedBy())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Project pr = pm.toEntity(project, user);

        return pm.toDto(projectRepository.save(pr));
    }

    public ProjectResponseDto updateProject(long id,ProjectRequestDto dto){
        Project Existingpr=projectRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Not found project with id: "+id));
        if(dto.getName()!=null){
            Existingpr.setName(dto.getName());
        }
        if(dto.getDescription()!=null){
            Existingpr.setDescription(dto.getDescription());
        }
        return pm.toDto(projectRepository.save(Existingpr));
    }

    public void deleteProject(long id) {
        projectRepository.deleteById(id);
    }
}
