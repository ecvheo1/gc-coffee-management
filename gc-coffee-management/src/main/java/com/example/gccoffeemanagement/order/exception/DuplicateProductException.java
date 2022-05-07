package com.example.gccoffeemanagement.order.exception;

import com.example.gccoffeemanagement.common.exception.BusinessException;
import com.example.gccoffeemanagement.common.exception.ErrorCode;

public class DuplicateProductException extends BusinessException {
    private static final ErrorCode errorCode = ErrorCode.PRODUCT_DUPLICATED;

    public DuplicateProductException() {
        super(errorCode);
    }
}
