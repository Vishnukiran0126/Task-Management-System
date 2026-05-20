package com.example.Task.controllers;

import com.example.Task.Dtos.LoginDto;
import com.example.Task.Dtos.LoginResponseDto;
import com.example.Task.Dtos.Pagination.PaginatedResponse;
import com.example.Task.Dtos.Pagination.PaginationRequest;
import com.example.Task.Dtos.UserRequestDto;
import com.example.Task.Dtos.UserResponseDto;
import com.example.Task.entites.User;
import com.example.Task.service.UserService;

import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000/")
public class UserController {

    private UserService userService; //make di field final

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<User> getUser(@PathVariable long id) {
        return new ResponseEntity<>(userService.getUserById(id), HttpStatus.OK);
    }

    @GetMapping("/users")
    public ResponseEntity<PaginatedResponse<UserResponseDto>> getUsers(@RequestParam(defaultValue = "0") Integer page,
                                                                            @RequestParam(defaultValue = "10") Integer size,
                                                                            @RequestParam(defaultValue = "id") String sortField,
                                                                            @RequestParam(defaultValue = "DESC") Sort.Direction direction,
                                                                            @RequestParam(required = false) String userName,
                                                                            @RequestParam(required = false) String userEmail) {
        PaginationRequest pr =PaginationRequest.builder()
                .page(page)
                .size(size)
                .sortField(sortField)
                .direction(direction)
                .build();

        return new ResponseEntity<>(userService.getAllUsers(pr,userName,userEmail), HttpStatus.OK);
    }

    @PostMapping("/user")
    public ResponseEntity<UserResponseDto> createUser(@RequestBody UserRequestDto user) {
        System.out.println("Called createUser");
        System.out.println(user.getEmail());
        return new ResponseEntity<>(userService.addUser(user), HttpStatus.CREATED);
    }

    @PutMapping("/user/{id}")
    public ResponseEntity<UserResponseDto> updateUser(@RequestBody UserRequestDto user, @PathVariable long id) {
        return new ResponseEntity<>(userService.updateUser(user, id), HttpStatus.OK);

    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity<User> deleteUser(@PathVariable long id) {
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginDto user){
        System.out.println("Logged in User: "+user.getUsername());
        return ResponseEntity.ok(userService.verify(user));
        //return "Success";// hard-coded value
    }
}
