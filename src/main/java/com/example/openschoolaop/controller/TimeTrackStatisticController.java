package com.example.openschoolaop.controller;

import com.example.openschoolaop.exception.handler.ApiErrorResponse;
import com.example.openschoolaop.model.MethodExecutionTimeInfo;
import com.example.openschoolaop.model.dto.GetExecutionTimeStatsRequest;
import com.example.openschoolaop.model.dto.MethodExecutionTimeInfoResponse;
import com.example.openschoolaop.service.TimeTrackStatisticService;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("stats")
public class TimeTrackStatisticController {

    private final TimeTrackStatisticService statisticService;

    @GetMapping(value = "/time/{className}/all", produces = "application/json")
    public ResponseEntity<Long> getAllTimeByClassNameByInterval(
            @PathVariable String className,
            @RequestHeader(required = false, name = "from") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime from,
            @RequestHeader(required = false, name = "to") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime to) {
        GetExecutionTimeStatsRequest statsRequest =
                new GetExecutionTimeStatsRequest(className, null, from, to);
        long value = statisticService.getAllTimeByClassNameByInterval(statsRequest);
        return ResponseEntity.ok(value);
    }

    @GetMapping(value = "/time/{className}/{methodName}/all", produces = "application/json")
    public ResponseEntity<Long> getAllTimeByClassNameAndMethodNameByInterval(
            @PathVariable String className,
            @PathVariable String methodName,
            @RequestHeader(required = false, name = "from") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime from,
            @RequestHeader(required = false, name = "to") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime to
    ) {
        GetExecutionTimeStatsRequest statsRequest =
                new GetExecutionTimeStatsRequest(className, methodName, from, to);
        long value = statisticService.getAllTimeByClassNameAndMethodNameByInterval(statsRequest);
        return ResponseEntity.ok(value);
    }

    @GetMapping(value = "/time/{className}/average", produces = "application/json")
    public ResponseEntity<Long> getAverageTimeByClassNameByInterval(
            @PathVariable String className,
            @RequestHeader(required = false, name = "from") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime from,
            @RequestHeader(required = false, name = "to") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime to
    ) {
        GetExecutionTimeStatsRequest statsRequest =
                new GetExecutionTimeStatsRequest(className, null, from, to);
        long value = statisticService.getAverageTimeByClassNameByInterval(statsRequest);
        return ResponseEntity.ok(value);
    }

    @GetMapping(value = "/time/{className}/{methodName}/average", produces = "application/json")
    public ResponseEntity<Long> getAverageTimeByClassNameAndMethodNameByInterval(
            @PathVariable String className,
            @PathVariable String methodName,
            @RequestHeader(required = false, name = "from") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime from,
            @RequestHeader(required = false, name = "to") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime to
    ) {
        GetExecutionTimeStatsRequest statsRequest =
                new GetExecutionTimeStatsRequest(className, methodName, from, to);
        long value = statisticService.getAverageTimeByClassNameAndMethodNameByInterval(statsRequest);
        return ResponseEntity.ok(value);
    }

    @GetMapping(value = {"/info/{className}", "/info/{className}/{methodName}"}, produces = "application/json")
    public ResponseEntity<MethodExecutionTimeInfoResponse> getMethodExecutionTimeInfoByInterval(
            @PathVariable String className,
            @PathVariable(required = false) String methodName,
            @RequestHeader(required = false, name = "from") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime from,
            @RequestHeader(required = false, name = "to") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime to
    ) {
        List<MethodExecutionTimeInfo> timeInfos = statisticService
                .getMethodExecutionTimeInfoByInterval(className, methodName, from, to);
        MethodExecutionTimeInfoResponse timeInfoResponse = new MethodExecutionTimeInfoResponse(timeInfos);
        return ResponseEntity.ok(timeInfoResponse);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiErrorResponse> methodArgumentTypeMismatchException(MethodArgumentTypeMismatchException exception) {
        ApiErrorResponse apiErrorResponse = ApiErrorResponse.builder()
                .exceptionName("MethodArgumentTypeMismatchException")
                .exceptionMessage(exception.getMessage())
                .description("Not valid 'from' or 'to' params in request headers")
                .code(String.valueOf(HttpStatus.BAD_REQUEST))
                .stacktrace(Arrays.stream(exception.getStackTrace())
                        .map(StackTraceElement::toString)
                        .toList())
                .build();
        return ResponseEntity.badRequest().body(apiErrorResponse);
    }
}
