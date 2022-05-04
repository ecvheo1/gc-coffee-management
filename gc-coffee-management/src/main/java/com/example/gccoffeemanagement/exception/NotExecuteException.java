package com.example.gccoffeemanagement.exception;

public class NotExecuteException extends RuntimeException {

    private static final ErrorCode errorCode = ErrorCode.NOT_EXECUTE_QUERY;

    public NotExecuteException() {
        super(errorCode.getMessage());
    }
}
