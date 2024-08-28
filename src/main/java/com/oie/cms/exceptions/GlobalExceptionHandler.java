package com.oie.cms.exceptions;

import com.oie.cms.dtos.common.ErrorDto;
import lombok.extern.log4j.Log4j2;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

// TODO: Return descriptive validation errors if possible
@ControllerAdvice
@Log4j2
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDto> handleException(Exception ex) {
        log.error(ex);

        HttpStatus errorStatus;
        if (ex instanceof MethodArgumentNotValidException
                || ex instanceof MethodArgumentTypeMismatchException
                || ex instanceof HttpMessageConversionException) {
            errorStatus = HttpStatus.BAD_REQUEST;
        } else if (ex instanceof AuthenticationException) {
            errorStatus = HttpStatus.UNAUTHORIZED;
        } else if (ex instanceof UnAuthorizedBusinessException) {
            errorStatus = HttpStatus.FORBIDDEN;
        } else if (ex instanceof NotFoundBusinessException) {
            errorStatus = HttpStatus.NOT_FOUND;
        } else if (ex instanceof HttpRequestMethodNotSupportedException) {
            errorStatus = HttpStatus.METHOD_NOT_ALLOWED;
        } else if (ex instanceof ConflictBusinessException
                || ex instanceof DataIntegrityViolationException) {
            errorStatus = HttpStatus.CONFLICT;
        } else if (ex instanceof UnProcessableBusinessException) {
            errorStatus = HttpStatus.UNPROCESSABLE_ENTITY;
        } else {
            errorStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        var errorMessage = ex instanceof BusinessException && ex.getMessage() != null
                ? ex.getMessage()
                : errorStatus.getReasonPhrase();

        return ResponseEntity.status(errorStatus).body(new ErrorDto(errorMessage));
    }
}
