package com.example.Task.service;

import com.example.Task.Dtos.Pagination.PaginatedResponse;
import com.example.Task.Dtos.Pagination.PaginationRequest;
import com.example.Task.Dtos.UserMapper;
import com.example.Task.Dtos.UserRequestDto;
import com.example.Task.Dtos.UserResponseDto;
import com.example.Task.Exception.ResourceNotFoundException;
import com.example.Task.Specification.UserSpecification;
import com.example.Task.entites.Task;
import com.example.Task.entites.User;
import com.example.Task.repositories.UserRepository;
import com.example.Task.utils.PaginationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private UserRepository userRepository;
    private final UserMapper um;

    @Autowired
    private PasswordEncoder encoder;
    public UserService(UserRepository userRepository,UserMapper um) {

        this.um=um;
        this.userRepository = userRepository;

    }

    public User getUserById(long id) {
        return userRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("User Not found with id: "+id));

    }

    public PaginatedResponse<UserResponseDto> getAllUsers(PaginationRequest pr,String userName,String userEmail) {
        Pageable pageable= PaginationUtils.getPageable(pr);//request to pageable object
        Specification<User> spec= UserSpecification.getAllUsers(userName,userEmail);//find and filetre based on the values
        //add generic type here as well
        Page<User> p = userRepository.findAll(spec,pageable);// returns a page of Users learn about page vs pageable vs paginationRequest

        PaginatedResponse<UserResponseDto> res=
                PaginatedResponse.<UserResponseDto>builder()
                        .size(p.getSize())
                        .page(p.getNumber())
                        .content(p.getContent().stream().map(um::toDto).toList())
                        .totalElements(p.getTotalElements())
                        .totalPages(p.getTotalPages())
                        .build();

        return res;
    }

    public UserResponseDto addUser(UserRequestDto user) {
        User newUser=um.toEntity(user);
        newUser.setPassword(encoder.encode(newUser.getPassword()));
        userRepository.save(newUser);
        return um.toDto(newUser);
    }

    public UserResponseDto updateUser(UserRequestDto user, long id) {
        User existingUser=getUserById(id);

        if(user.getName()!=null){
            existingUser.setName(user.getName());
        }
        if(user.getEmail()!=null){
            existingUser.setEmail(user.getEmail());
        }
        if(user.getPassword()!=null){
            existingUser.setPassword(user.getPassword());
        }
        if(user.getRole()!=null){
            existingUser.setRole(user.getRole());
        }
        userRepository.save(existingUser);
        return um.toDto(existingUser);
    }

    public void deleteUser(long id) {
        userRepository.deleteById(id);
    }


}
