package com.ilabs.manageitemsservices.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class InvalidBadRequestException extends RuntimeException {
    private final String property;

    private final String message;
}
