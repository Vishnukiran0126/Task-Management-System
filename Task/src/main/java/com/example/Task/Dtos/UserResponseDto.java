package com.example.Task.Dtos;


import com.example.Task.entites.Project;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDto {

    private Long id;

    private String name;
    private String email;
    private String role;
   // private List<Project> projects;
}
