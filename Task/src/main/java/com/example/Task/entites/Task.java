package com.example.Task.entites;

import com.example.Task.enums.TaskPriority;
import com.example.Task.enums.TaskStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;

    @Enumerated(EnumType.STRING)
    private TaskStatus status;

    @Enumerated(EnumType.STRING)
    private TaskPriority priority;
    private LocalDate dueDate;

    @ManyToOne()
    @JoinColumn(name="project_id", referencedColumnName = "id")
    private Project project;// ManyToOne need an Entity not a primitive!!!!!

    //Without referencedColumnName also spring detects id as primary key and creates a foreign key

    @ManyToOne()
    @JoinColumn(name="assigned_user", referencedColumnName = "id")
    private User assignedUser;//foreign key

    @OneToMany(mappedBy = "task")
    @JsonIgnore
    private List<Comment> comments;
}
