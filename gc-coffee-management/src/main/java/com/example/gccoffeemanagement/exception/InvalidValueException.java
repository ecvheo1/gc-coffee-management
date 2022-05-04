package com.example.gccoffeemanagement.exception;

public class InvalidValueException extends RuntimeException {
    private final ErrorCode errorCode;

    public InvalidValueException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
