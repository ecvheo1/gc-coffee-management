package com.example.gccoffeemanagement.exception;

public class DuplicateProductException extends BusinessException {
    private static final ErrorCode errorCode = ErrorCode.PRODUCT_DUPLICATED;

    public DuplicateProductException() {
        super(errorCode);
    }
}
