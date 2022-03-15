package com.mts.teta.courses.dto.exception;

import java.time.OffsetDateTime;

public class ApiError {
    private String message;
    OffsetDateTime dateOccurred;

    public ApiError() {
        this.dateOccurred = OffsetDateTime.now();
    }

    public ApiError(String message) {
        this.message = message;
        this.dateOccurred = OffsetDateTime.now();
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
