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

    @GetMapping("trackTime")
    public void trackTime() {
        testService.methodRandomExecutionTime();
    }

    @GetMapping("trackAsyncTime")
    public ResponseEntity<Long> trackAsyncTime() {
        Long l = testService.methodRandomExecutionTimeAsync();
        return ResponseEntity.ok(l);
    }
}
