package com.example.testall.mcp;

import java.util.UUID;

public class ErrorResponse {

    private UUID errorId;

    private String message;

    public UUID getErrorId() {
        return errorId;
    }

    public void setErrorId(UUID errorId) {
        this.errorId = errorId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
