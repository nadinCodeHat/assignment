package com.ilabs.authservice.exception;

import com.ilabs.authservice.dto.ErrorResponseDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.repository.core.support.MethodLookup;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(MethodLookup.class);

    @ExceptionHandler(value = TaskNotFoundException.class)
    protected ResponseEntity<ErrorResponseDto> handleTaskNotFoundException(TaskNotFoundException ex) {
        ErrorResponseDto responseDto = ErrorResponseDto
                .builder()
                .message(ex.getMessage())
                .build();

        logger.error("NO TASK FOUND EXCEPTION : {}", responseDto);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDto);
    }

    @ExceptionHandler(value = UserNotFoundException.class)
    protected ResponseEntity<ErrorResponseDto> handleUserNotFoundException(UserNotFoundException ex) {
        ErrorResponseDto responseDto = ErrorResponseDto
                .builder()
                .message(ex.getMessage())
                .build();

        logger.error("NO USER FOUND EXCEPTION : {}", responseDto);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDto);
    }

    @ExceptionHandler(value = RuntimeException.class)
    protected final ResponseEntity<ErrorResponseDto> handleRuntimeExceptions(RuntimeException ex) {
        ErrorResponseDto responseDto = ErrorResponseDto
                .builder()
                .message("Unpredicted error")
                .build();

        logger.error("RUNTIME EXCEPTION OCCURRED: {}", responseDto);

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseDto);
    }

    @ExceptionHandler(value = Exception.class)
    protected final ResponseEntity<ErrorResponseDto> handleGeneralExceptions(Exception ex) {
        ErrorResponseDto responseDto = ErrorResponseDto
                .builder()
                .message("Unpredicted error")
                .build();

        logger.error("EXCEPTION OCCURRED: {}", responseDto);

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseDto);
    }
}
