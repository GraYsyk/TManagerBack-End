package com.graysenko.FullstackTM.Entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.bind.DefaultValue;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter @Setter
public class Task {

    @Id
    @Column(unique = true, nullable = false, name= "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, nullable = false, name = "name")
    private String name;

    private String description;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> tags;

    private Boolean completed = false;


    public Task() {
    }

    public Task(String name, String description, List<String> tags, Boolean completed) {
        this.name = name;
        this.description = description;
        this.tags = tags;
        this.completed = completed;
    }
}
