package com.example.Task.controllers;

import com.example.Task.Dtos.Pagination.PaginatedResponse;
import com.example.Task.Dtos.Pagination.PaginationRequest;
import com.example.Task.Dtos.TaskRequestDto;
import com.example.Task.Dtos.TaskResponseDto;
import com.example.Task.entites.Task;
import com.example.Task.enums.TaskPriority;
import com.example.Task.enums.TaskStatus;
import com.example.Task.service.TaskService;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

import static org.hibernate.query.Page.page;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class TaskController {

    private TaskService taskService;
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/tasks")
    public ResponseEntity<PaginatedResponse<TaskResponseDto>> getAllTasks(
                                                                     @RequestParam(defaultValue = "0") Integer page,
                                                                     @RequestParam(defaultValue = "10") Integer size,
                                                                     @RequestParam(defaultValue = "id") String sortField,
                                                                     @RequestParam(defaultValue = "DESC") Sort.Direction direction,
                                                                     @RequestParam(required = false) TaskStatus status,
                                                                     @RequestParam(required = false) TaskPriority priority,
                                                                     @DateTimeFormat(pattern="dd-MM-yyyy")
                                                                     @RequestParam(required = false) LocalDate dueDate,
                                                                     @DateTimeFormat(pattern="dd-MM-yyyy")
                                                                     @RequestParam(required = false) LocalDate dueDateFrom,
                                                                     @DateTimeFormat(pattern="dd-MM-yyyy")
                                                                     @RequestParam(required = false) LocalDate dueDateTo,
                                                                     @RequestParam(required = false) String name,
                                                                     @RequestParam(required = false) Long projectId) {

        PaginationRequest pr=PaginationRequest.builder()
                .page(page)
                .size(size)
                .sortField(sortField)
                .direction(direction)
                .build();

//        if(status!=null && priority!=null &&dueDate!=null){
//            return new ResponseEntity<>( taskService.getAllTasksByStatusAndPriorityAndDueDate(status, priority,dueDate,pr), HttpStatus.OK);
//        }
//        else if(status!=null && priority!=null){
//            return new ResponseEntity<>( taskService.getAllTasksByStatusAndPriority(status, priority,pr), HttpStatus.OK);
//        }
//        else if(status!=null && dueDate!=null){
//            return new ResponseEntity<>( taskService.getAllTasksByStatusAndDueDate(status,dueDate,pr), HttpStatus.OK);
//        }
//        else if(priority!=null && dueDate!=null){
//            return new ResponseEntity<>( taskService.getAllTasksByPriorityAndDueDate(priority,dueDate,pr), HttpStatus.OK);
//        }
//        else if(status!=null){
//            return new ResponseEntity<>( taskService.getAllTasksByStatus(status,pr), HttpStatus.OK);
//        }
//        else if(priority!=null){
//            return new ResponseEntity<>( taskService.getAllTasksByPriority(priority,pr), HttpStatus.OK);
//        }
//        else if(dueDate!=null){
//            return new ResponseEntity<>( taskService.getAllTasksByDueDate(dueDate,pr), HttpStatus.OK);
//        }

        return new ResponseEntity<>( taskService.getAllTasks(pr, status,priority,dueDate,dueDateFrom,dueDateTo,name,projectId), HttpStatus.OK);
    }

    @GetMapping("/task/{taskId}")
    public ResponseEntity<TaskResponseDto> getTaskById(@PathVariable long taskId){
        return new ResponseEntity<>(taskService.getTaskById(taskId), HttpStatus.OK);

    }

//    @GetMapping("/tasks/project/{projectId}")
//    public ResponseEntity<PaginatedResponse<TaskResponseDto>> getAlltasksByprojectId(@PathVariable Long projectId,
//                                                                                     @RequestParam(defaultValue = "5") Integer size,
//                                                                                     @RequestParam(defaultValue = "0") Integer page,
//                                                                                     @RequestParam(defaultValue = "ASC") Sort.Direction direction,
//                                                                                     @RequestParam(defaultValue = "id") String sortField,
//                                                                                     @RequestParam(required = false) TaskStatus status){
//
//        PaginationRequest paginationRequest=PaginationRequest.builder()
//                .page(page)
//                .size(size)
//                .sortField(sortField)
//                .direction(direction)
//                .build(); // a pagination Request is now created
//
//
//        if(status!=null){
//            return new ResponseEntity<>(taskService.getAllTasksByProjectIdAndStatus(paginationRequest,projectId,status),HttpStatus.OK);
//        }
//
//        return new ResponseEntity<>(taskService.getAllTasksByProjectId(paginationRequest, projectId),HttpStatus.OK);
//
//    }


    @PostMapping("/task")
    public ResponseEntity<TaskResponseDto> createTask(@RequestBody TaskRequestDto task) {
        return new ResponseEntity<>(taskService.addTask(task), HttpStatus.OK);
    }

    @PutMapping("/task/{taskId}")
    public ResponseEntity<TaskResponseDto> updateTask(@PathVariable long taskId, @RequestBody TaskRequestDto task) {
        return new ResponseEntity<>(taskService.updateTask(task, taskId), HttpStatus.OK);
    }

    @DeleteMapping("/task/{taskId}")
    public ResponseEntity<?> deleteTask(@PathVariable long taskId) {
        taskService.deleteTask(taskId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/dashboard")
    public ResponseEntity<HashMap<String,Long>> getDashboardData(){
        HashMap<String,Long> hm =taskService.getDashboardStats();
        return ResponseEntity.ok(hm);
    }

}
