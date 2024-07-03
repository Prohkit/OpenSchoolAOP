package com.example.openschoolaop.exception.handler;

import com.example.openschoolaop.exception.TestMethodInterruptedException;
import com.example.openschoolaop.exception.TrackTimeException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Arrays;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(TrackTimeException.class)
    public ResponseEntity<ApiErrorResponse> trackTimeException(TrackTimeException exception) {
        ApiErrorResponse apiErrorResponse = getApiErrorResponse(exception, HttpStatus.INTERNAL_SERVER_ERROR);
        apiErrorResponse.setExceptionName("TrackTimeException");
        apiErrorResponse.setDescription("Method execution time tracking logging error.");
        return ResponseEntity
                .internalServerError()
                .body(apiErrorResponse);
    }

    @ExceptionHandler(TestMethodInterruptedException.class)
    public ResponseEntity<ApiErrorResponse> testMethodInterruptedException(TestMethodInterruptedException exception) {
        ApiErrorResponse apiErrorResponse = getApiErrorResponse(exception, HttpStatus.INTERNAL_SERVER_ERROR);
        apiErrorResponse.setExceptionName("TestMethodInterruptedException");
        apiErrorResponse.setDescription("Method for check track time annotations war interrupted.");
        return ResponseEntity
                .internalServerError()
                .body(apiErrorResponse);
    }

    private ApiErrorResponse getApiErrorResponse(Exception exception, HttpStatus httpStatus) {
        return ApiErrorResponse.builder()
                .exceptionMessage(exception.getMessage())
                .code(String.valueOf(httpStatus))
                .stacktrace(Arrays.stream(exception.getStackTrace())
                        .map(StackTraceElement::toString)
                        .toList())
                .build();
    }
}
