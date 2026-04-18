package com.example.Task.repositories;

import com.example.Task.entites.Task;
import com.example.Task.enums.TaskPriority;
import com.example.Task.enums.TaskStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.time.LocalDate;
import java.util.List;

public interface TaskRepository extends JpaRepository<Task,Long>, JpaSpecificationExecutor<Task> {

//    public Page<Task> findTasksByProjectId(long projectId, Pageable pageable);
////
//    public Page<Task> findTasksByProjectIdAndStatus(long projectId, TaskStatus status, Pageable pageable);
//
//    public Page<Task> findTasksByStatus(TaskStatus status, Pageable pageable);
//
//    public Page<Task> findTasksByPriority(TaskPriority priority, Pageable pageable);
//
//    public Page<Task> findTasksByDueDate(LocalDate dueDate, Pageable pageable);
//
//    public Page<Task> findTasksByStatusAndPriority(TaskStatus status,TaskPriority priority, Pageable pageable);
//
//    public Page<Task> findTasksByStatusAndDueDate(TaskStatus status, LocalDate dueDate,Pageable pageable);
//
//    public Page<Task> findTasksByPriorityAndDueDate(TaskPriority priority, LocalDate dudate,Pageable pageable);
//
//    public Page<Task> findTasksByStatusAndPriorityAndDueDate(TaskStatus status, TaskPriority priority, LocalDate date, Pageable pageable);
}
