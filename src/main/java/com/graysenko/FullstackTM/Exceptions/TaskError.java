package com.graysenko.FullstackTM.Exceptions;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter @Setter
public class TaskError {

    private int status;
    private String message;
    private Date timestamp;

    public TaskError(int status, String message) {
        this.status = status;
        this.message = message;
    }
}
