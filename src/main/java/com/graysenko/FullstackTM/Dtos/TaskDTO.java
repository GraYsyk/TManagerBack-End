package com.graysenko.FullstackTM.Dtos;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter @Setter
public class TaskDTO {

    private Long id;
    private String name;
    private String description;
    private List<String> tags;
    private Boolean completed;

    public TaskDTO(String name, String description, List<String> tags, Boolean completed) {
        this.name = name;
        this.description = description;
        this.tags = tags;
        this.completed = completed;
    }

    public TaskDTO() {}
}
