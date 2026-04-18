package com.example.Task.repositories;

import com.example.Task.entites.Project;
import com.example.Task.entites.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project,Long>, JpaSpecificationExecutor<Project> {

    public Project getProjectByName(String name);// jpa so list<project> is allowed

}
