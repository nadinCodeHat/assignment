package com.ilabs.cartservice.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class InvalidRequestDataException extends RuntimeException {

    private final String message;
}
