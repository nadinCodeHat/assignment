package com.ilabs.authservice.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorResponseDto {

    private String message;

    @Override
    public String toString() {
        return String.format("Error response(message=%s)", message);
    }
}
