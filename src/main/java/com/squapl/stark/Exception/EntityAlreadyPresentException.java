package com.squapl.stark.Exception;

import lombok.Data;

@Data
public class EntityAlreadyPresentException extends RuntimeException {
    private static final long serialVersionID = 1L;

    private String message;

    public EntityAlreadyPresentException(String message) {
        this.setMessage(message);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
