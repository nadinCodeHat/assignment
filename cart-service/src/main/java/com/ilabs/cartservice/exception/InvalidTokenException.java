package com.ilabs.cartservice.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class InvalidTokenException extends RuntimeException {

    private final String token;

    private final String message;
}
