package com.sharemarket.invest.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        List<ErrorMessage> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> ErrorMessage.builder()
                        .httpStatus(HttpStatus.BAD_REQUEST.value())
                        .fields(error.getField())
                        .message(error.getDefaultMessage())
                        .build())
                .toList();
        return ResponseEntity.badRequest().headers(headers).body(errors);
    }

    @ExceptionHandler(CustomInvestException.class)
    public ResponseEntity<Object> handleCustomInvestException(HttpServletRequest request, CustomInvestException exception) {

        ErrorMessage error = ErrorMessage.builder()
                .httpStatus(HttpStatus.BAD_REQUEST.value())
                .fields("")
                .message(exception.getMessage())
                .build();

        return ResponseEntity.badRequest().body(error);
    }

}

