package com.example.openschoolaop.controller;

import com.example.openschoolaop.service.TestService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class TestController {

    private final TestService testService;

    @GetMapping(value = "trackTime", produces = "application/json")
    public ResponseEntity<Long> trackTime() {
        Long methodExecTimeMillis = testService.methodRandomExecutionTime();
        return ResponseEntity.ok(methodExecTimeMillis);
    }

    @GetMapping(value = "trackAsyncTime", produces = "application/json")
    public ResponseEntity<Long> trackAsyncTime() {
        Long methodExecTimeMillis = testService.methodRandomExecutionTimeAsync();
        return ResponseEntity.ok(methodExecTimeMillis);
    }
}
