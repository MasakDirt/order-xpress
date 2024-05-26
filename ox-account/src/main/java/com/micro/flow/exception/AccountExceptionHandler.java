package com.micro.flow.exception;

import com.micro.flow.dto.ErrorResponse;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.*;

@Slf4j
@RestControllerAdvice
public class AccountExceptionHandler {
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ErrorResponse> handleResponseStatusException(
            HttpServletRequest request, ResponseStatusException ex) {
        return getErrorResponse(request, ex.getStatusCode(), ex.getReason());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(
            HttpServletRequest request, MethodArgumentNotValidException ex) {
        String message = ex.getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining(", "));

        return getErrorResponse(request, BAD_REQUEST, message);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolationException(
            HttpServletRequest request, ConstraintViolationException ex) {
        return getErrorResponse(request, BAD_REQUEST, ex.getLocalizedMessage());
    }

    @ExceptionHandler({IllegalArgumentException.class, BalanceException.class})
    public ResponseEntity<ErrorResponse> handleBadRequestExceptions(
            HttpServletRequest request, RuntimeException ex) {
        return getErrorResponse(request, BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handleAccessDeniedException(
            HttpServletRequest request, AccessDeniedException ex) {
        return getErrorResponse(request, FORBIDDEN, ex.getMessage());
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundExceptions(
            HttpServletRequest request, EntityNotFoundException ex) {
        return getErrorResponse(request, NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(
            HttpServletRequest request, Exception ex) {
        return getErrorResponse(request, INTERNAL_SERVER_ERROR, ex.getMessage());
    }

    private ResponseEntity<ErrorResponse> getErrorResponse(
            HttpServletRequest request, HttpStatusCode httpStatus, String message) {
        log.error("Exception raised = {} :: URL = {}", message, request.getRequestURL());
        return ResponseEntity.status(httpStatus)
                .body(new ErrorResponse(
                                LocalDateTime.now(),
                                httpStatus,
                                message,
                                request.getRequestURL().toString()
                        )
                );
    }

}
