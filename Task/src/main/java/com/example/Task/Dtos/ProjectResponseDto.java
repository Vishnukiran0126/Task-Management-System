package com.example.Task.Dtos;


import com.example.Task.entites.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProjectResponseDto {

    private Long projectId;
    private String name;
    private String description;
    private String createdByName;
    private Long createdById;
}
