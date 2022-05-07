package com.example.gccoffeemanagement.common.exception;

public class NotExecuteException extends RuntimeException {

    private static final ErrorCode errorCode = ErrorCode.NOT_EXECUTE_QUERY;

    public NotExecuteException() {
        super(errorCode.getMessage());
    }
}
