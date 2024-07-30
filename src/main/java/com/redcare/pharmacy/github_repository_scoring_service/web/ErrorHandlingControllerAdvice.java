package com.redcare.pharmacy.github_repository_scoring_service.web;

import io.swagger.v3.oas.annotations.Hidden;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

/**
 * Error handling controller advice for the exception handling.
 * Hanldes the application specific exceptions and returns {@link ProblemDetail} response with details.
 */
@Hidden
@RestControllerAdvice
@Slf4j
public class ErrorHandlingControllerAdvice {

    @ExceptionHandler(ConstraintViolationException.class)
    public ProblemDetail onConstraintValidationException(final ConstraintViolationException ex) {
        log.warn("Validation error", ex);
        final ProblemDetail pd = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getMessage());
        pd.setProperties(ex.getConstraintViolations().stream().collect(Collectors.toMap(f -> f.getPropertyPath().toString(), f -> f.getMessage())));
        return pd;
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ProblemDetail onIllegalArgumentException(final IllegalArgumentException ex) {
        log.warn("Exception", ex);
        return getProblemDetail(ex, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ProblemDetail onException(final Exception ex) {
        log.warn("Exception", ex);
        return getProblemDetail(ex, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private static ProblemDetail getProblemDetail(final Exception ex, final HttpStatus status) {
        final ProblemDetail pd = ProblemDetail.forStatusAndDetail(status, ex.getMessage());
        pd.setProperty("timestamp", LocalDateTime.now());
        return pd;
    }

}
