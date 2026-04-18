package com.example.Task.service;

import com.example.Task.Dtos.Pagination.PaginatedResponse;
import com.example.Task.Dtos.Pagination.PaginationRequest;
import com.example.Task.Dtos.TaskMapper;
import com.example.Task.Dtos.TaskRequestDto;
import com.example.Task.Dtos.TaskResponseDto;
import com.example.Task.Exception.ResourceNotFoundException;
import com.example.Task.Specification.TaskSpecification;
import com.example.Task.entites.Task;
import com.example.Task.entites.User;
import com.example.Task.enums.TaskPriority;
import com.example.Task.enums.TaskStatus;
import com.example.Task.repositories.TaskRepository;
import com.example.Task.utils.PaginationUtils;
import org.springframework.boot.data.autoconfigure.web.DataWebProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class TaskService {

    private final TaskMapper tm;
    private final UserService userService;
    private TaskRepository taskRepository;
    private ProjectService ps;

    public TaskService(TaskRepository taskRepository, UserService userService,TaskMapper tm, ProjectService ps) {
        this.taskRepository = taskRepository;
        this.userService = userService;
        this.tm=tm;
        this.ps=ps;
    }

    public PaginatedResponse<TaskResponseDto> getAllTasks(PaginationRequest pr,TaskStatus status, TaskPriority priority, LocalDate dueDate,LocalDate dueDateFrom,LocalDate dueDateTo,String name,Long projectId) {
        Pageable pageable= PaginationUtils.getPageable(pr);//request to pageable obj

        Specification<Task> spec = TaskSpecification.getTasks(status,priority,dueDate,dueDateFrom,dueDateTo,name,projectId);// what is returned from here is of Specification type we are using Specifcation interface to hold the value


        Page<Task> p=taskRepository.findAll(spec,pageable);
        System.out.println(p);
        PaginatedResponse<TaskResponseDto> paginatedResponse= PaginatedResponse.<TaskResponseDto>builder()
                .size(p.getSize())
                .content(p.getContent().stream().map(tm::toDto).toList())
                .totalElements(p.getTotalElements())
                .totalPages(p.getTotalPages())
                .page(p.getNumber())
                .build();
        return paginatedResponse;

    }

//    public PaginatedResponse<TaskResponseDto> getAllTasksByStatus(TaskStatus status,PaginationRequest pr) {
//        Pageable pageable= PaginationUtils.getPageable(pr);
//        Page<Task> p=taskRepository.findTasksByStatus(status,pageable);
//        System.out.println(p);
//        PaginatedResponse<TaskResponseDto> paginatedResponse= PaginatedResponse.<TaskResponseDto>builder()
//                .size(p.getSize())
//                .content(p.getContent().stream().map(tm::toDto).toList())
//                .totalElements(p.getTotalElements())
//                .totalPages(p.getTotalPages())
//                .page(p.getNumber())
//                .build();
//        return paginatedResponse;
//
//    }
//
//    public PaginatedResponse<TaskResponseDto> getAllTasksByPriority(TaskPriority priority, PaginationRequest pr) {
//        Pageable pageable= PaginationUtils.getPageable(pr);
//        Page<Task> p=taskRepository.findTasksByPriority(priority,pageable);
//        System.out.println(p);
//        PaginatedResponse<TaskResponseDto> paginatedResponse= PaginatedResponse.<TaskResponseDto>builder()
//                .size(p.getSize())
//                .content(p.getContent().stream().map(tm::toDto).toList())
//                .totalElements(p.getTotalElements())
//                .totalPages(p.getTotalPages())
//                .page(p.getNumber())
//                .build();
//        return paginatedResponse;
//
//    }
//
//    public PaginatedResponse<TaskResponseDto> getAllTasksByDueDate(LocalDate dueDate, PaginationRequest pr) {
//        Pageable pageable= PaginationUtils.getPageable(pr);
//        Page<Task> p=taskRepository.findTasksByDueDate(dueDate,pageable);
//        System.out.println(p);
//        PaginatedResponse<TaskResponseDto> paginatedResponse= PaginatedResponse.<TaskResponseDto>builder()
//                .size(p.getSize())
//                .content(p.getContent().stream().map(tm::toDto).toList())
//                .totalElements(p.getTotalElements())
//                .totalPages(p.getTotalPages())
//                .page(p.getNumber())
//                .build();
//        return paginatedResponse;
//
//    }
//
//    public PaginatedResponse<TaskResponseDto> getAllTasksByStatusAndPriority(TaskStatus status,TaskPriority priority,PaginationRequest pr) {
//        Pageable pageable= PaginationUtils.getPageable(pr);
//        Page<Task> p=taskRepository.findTasksByStatusAndPriority(status,priority,pageable);
//        System.out.println(p);
//        PaginatedResponse<TaskResponseDto> paginatedResponse= PaginatedResponse.<TaskResponseDto>builder()
//                .size(p.getSize())
//                .content(p.getContent().stream().map(tm::toDto).toList())
//                .totalElements(p.getTotalElements())
//                .totalPages(p.getTotalPages())
//                .page(p.getNumber())
//                .build();
//        return paginatedResponse;
//
//    }
//
//    public PaginatedResponse<TaskResponseDto> getAllTasksByStatusAndDueDate(TaskStatus status,LocalDate dueDate,PaginationRequest pr) {
//        Pageable pageable= PaginationUtils.getPageable(pr);
//        Page<Task> p=taskRepository.findTasksByStatusAndDueDate(status,dueDate,pageable);
//        System.out.println(p);
//        PaginatedResponse<TaskResponseDto> paginatedResponse= PaginatedResponse.<TaskResponseDto>builder()
//                .size(p.getSize())
//                .content(p.getContent().stream().map(tm::toDto).toList())
//                .totalElements(p.getTotalElements())
//                .totalPages(p.getTotalPages())
//                .page(p.getNumber())
//                .build();
//        return paginatedResponse;
//
//    }
//
//    public PaginatedResponse<TaskResponseDto> getAllTasksByPriorityAndDueDate(TaskPriority priority,LocalDate dueDate,PaginationRequest pr) {
//        Pageable pageable= PaginationUtils.getPageable(pr);
//        Page<Task> p=taskRepository.findTasksByPriorityAndDueDate(priority,dueDate,pageable);
//        System.out.println(p);
//        PaginatedResponse<TaskResponseDto> paginatedResponse= PaginatedResponse.<TaskResponseDto>builder()
//                .size(p.getSize())
//                .content(p.getContent().stream().map(tm::toDto).toList())
//                .totalElements(p.getTotalElements())
//                .totalPages(p.getTotalPages())
//                .page(p.getNumber())
//                .build();
//        return paginatedResponse;
//
//    }
//
//    public PaginatedResponse<TaskResponseDto> getAllTasksByStatusAndPriorityAndDueDate(TaskStatus status,TaskPriority priority,LocalDate dueDate,PaginationRequest pr) {
//        Pageable pageable= PaginationUtils.getPageable(pr);
//        Page<Task> p=taskRepository.findTasksByStatusAndPriorityAndDueDate(status,priority,dueDate,pageable);
//        System.out.println(p);
//        PaginatedResponse<TaskResponseDto> paginatedResponse= PaginatedResponse.<TaskResponseDto>builder()
//                .size(p.getSize())
//                .content(p.getContent().stream().map(tm::toDto).toList())
//                .totalElements(p.getTotalElements())
//                .totalPages(p.getTotalPages())
//                .page(p.getNumber())
//                .build();
//        return paginatedResponse;
//
//    }

    public TaskResponseDto addTask(TaskRequestDto task) {
        Task t=tm.toEntity(task);
        taskRepository.save(t);
        return tm.toDto(t);
    }

    public TaskResponseDto updateTask(TaskRequestDto taskdto, long taskId) {
        Task ExistingTask=taskRepository.findById(taskId).orElseThrow(()-> new ResourceNotFoundException("Not found task with id: "+taskId));
       // Task task=tm.toEntity(taskdto);// received task
        //task.setId(taskId);// received task with Id

        if(taskdto.getTitle() != null)
            ExistingTask.setTitle(taskdto.getTitle());

        if(taskdto.getDescription() != null)
            ExistingTask.setDescription(taskdto.getDescription());

        if(taskdto.getStatus() != null)
            ExistingTask.setStatus(taskdto.getStatus());

        if(taskdto.getPriority() != null)
            ExistingTask.setPriority(taskdto.getPriority());

        if(taskdto.getDueDate() != null)
            ExistingTask.setDueDate(taskdto.getDueDate());

        if(taskdto.getAssignedUser() != null) {
            User user = userService.getUserById(taskdto.getAssignedUser()); //first fetch and see if the user exists
            //otherwise while, returning you will see only what you updated

            if(user!=null)
                ExistingTask.setAssignedUser(user);
        }

        if(taskdto.getProjectId()!=null)
            ExistingTask.setProject(ps.getProjectById(taskdto.getProjectId()));

        taskRepository.save(ExistingTask);

        return tm.toDto(ExistingTask);// returning final updated Task

    }

    public void deleteTask(long taskId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Task not found with id: " + taskId));

        taskRepository.delete(task);
    }

    public List<Task> getAllTasks() {

        return taskRepository.findAll();
    }

    public TaskResponseDto getTaskById(long taskId) {
        return tm.toDto(taskRepository.findById(taskId).orElseThrow(()->new ResourceNotFoundException("Task with id can't be found!")));

    }

//    public PaginatedResponse<TaskResponseDto> getAllTasksByProjectId(PaginationRequest pr, Long projectId) {
//
//        Pageable p=PaginationUtils.getPageable(pr);// covert the request into pageable obj
//
//        Page<Task> tasks= taskRepository.findTasksByProjectId(projectId,p); // the return Type should be specified in the repository for methods created by us
//
//        return PaginatedResponse.<TaskResponseDto>builder()
//                .size(tasks.getSize())
//                .content(tasks.getContent().stream().map(tm::toDto).toList())
//                .page(tasks.getNumber())
//                .totalPages(tasks.getTotalPages())
//                .totalElements(tasks.getTotalElements())
//                .build();
//
//    }
//
//    public PaginatedResponse<TaskResponseDto> getAllTasksByProjectIdAndStatus(PaginationRequest paginationRequest, Long id, TaskStatus status) {
//
//        Pageable pageable=PaginationUtils.getPageable(paginationRequest);
//
//        Page<Task> tasks=taskRepository.findTasksByProjectIdAndStatus(id,status,pageable);
//
//        return PaginatedResponse.<TaskResponseDto>builder()
//                .size(tasks.getSize())
//                .totalElements(tasks.getTotalElements())
//                .totalPages(tasks.getTotalPages())
//                .page(tasks.getNumber())
//                .content(tasks.getContent().stream().map(tm::toDto).toList())
//                .build();
//    }
}
