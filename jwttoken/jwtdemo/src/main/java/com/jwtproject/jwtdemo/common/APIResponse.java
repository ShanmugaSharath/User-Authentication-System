package com.jwtproject.jwtdemo.common;

import lombok.Data;

@Data
public class APIResponse {
    private int status;
    private Object data;
    private String error;

    // Constructors (optional)
    public APIResponse() {
        this.status = 200;
        this.error = null; // Default error to null
    }

    // Getters and Setters
    
}
