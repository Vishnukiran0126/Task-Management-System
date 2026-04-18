package com.example.Task.entites;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    @JsonIgnore
    private String password;
    private String role;

    @OneToMany(mappedBy = "createdBy")
    @JsonIgnore
    private List<Project> projects;

    @OneToMany(mappedBy = "assignedUser")
    @JsonIgnore
    private List<Task> tasks;

    @OneToMany(mappedBy="user")
    @JsonIgnore
    private List<Comment> comments;
}
