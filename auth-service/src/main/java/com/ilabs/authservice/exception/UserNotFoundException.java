package com.ilabs.authservice.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserNotFoundException extends RuntimeException {
    private final String statusText;
    private final String message;
}
