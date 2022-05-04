package com.example.gccoffeemanagement.domain;

import com.example.gccoffeemanagement.exception.ErrorCode;
import com.example.gccoffeemanagement.exception.InvalidValueException;

import java.util.Arrays;

public enum Category {
    BLONDE_ROAST("BLONDE_ROAST"),
    MEDIUM_ROAST("MEDIUM_ROAST"),
    DARK_ROAST("DARK_ROAST");

    private final String category;

    Category(String category) {
        this.category = category;
    }

    public static Category from(String category) {
        return Arrays.stream(Category.values())
                .filter(type -> type.category.equals(category))
                .findFirst()
                .orElseThrow(() -> new InvalidValueException(ErrorCode.CATEGORY_NOT_FOUND));
    }
}
