package com.example.Task.Dtos;

import com.example.Task.entites.Project;
import com.example.Task.entites.User;
import com.example.Task.service.ProjectService;
import com.example.Task.service.UserService;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    //private final UserService userService;
   // private final ProjectService ps;

//    public UserMapper(UserService userService, ProjectService ps) {
//       // this.userService = userService;
//        this.ps=ps;
//    }

    public User toEntity(UserRequestDto dto){
        User user=new User();
        user.setName(dto.getName());
        user.setRole(dto.getRole());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
       // user.setProjects(dto.getProjects().stream().map(ps::getProjectById).toList());
        return user;
    }

    public UserResponseDto toDto(User user){

        UserResponseDto dto =new UserResponseDto();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setRole(user.getRole());
       // dto.setProjects(user.getProjects());
        return dto;
    }


}
