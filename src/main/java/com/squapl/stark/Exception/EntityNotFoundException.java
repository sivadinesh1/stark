package com.squapl.stark.Exception;


import lombok.Data;

@Data
public class EntityNotFoundException extends RuntimeException {
    private static final long serialVersionID = 1L;

    private String message;

    public EntityNotFoundException() {

    }

    public EntityNotFoundException(String message) {
        this.setMessage(message);
    }

    public EntityNotFoundException(Long id, String message) {
        this.setMessage(String.valueOf(id) + ": " + message);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
