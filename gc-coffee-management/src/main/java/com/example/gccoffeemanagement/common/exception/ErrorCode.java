package com.example.gccoffeemanagement.common.exception;

public enum ErrorCode {

    INTERNAL_SERVER_ERROR(500,  "서버 내부에 오류가 생겼습니다."),
    NULL_INPUT_OBJECT(500, "애플리케이션이 Null을 사용하려고 합니다."),
    NOT_EXECUTE_QUERY(500, "쿼리 실행이 실패하였습니다."),

    INVALID_INPUT_VALUE(400, "적절하지 않은 요청 값입니다."),
    INVALID_TYPE_VALUE(400,  "요청 값의 타입이 잘못되었습니다."),
    PRODUCT_DUPLICATED(400, "중복된 물품을 추가할 수 없습니다."),
    CATEGORY_NOT_FOUND(400, "해당 용어의 카테고리는 존재하지 않습니다.");

    private final int status;
    private final String message;

    ErrorCode(final int status, final String message) {
        this.status = status;
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }

    public int getStatus() {
        return status;
    }
}
