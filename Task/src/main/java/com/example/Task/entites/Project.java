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
@AllArgsConstructor
@NoArgsConstructor
public class Project {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;

    @ManyToOne()
    @JoinColumn(name="created_by", referencedColumnName = "id")
    private User createdBy;//foreign key

    @OneToMany(mappedBy = "project")
    @JsonIgnore
    private List<Task> tasks;
}
