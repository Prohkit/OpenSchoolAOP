package com.example.openschoolaop.controller;

import com.example.openschoolaop.service.TimeTrackStatisticService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("stats")
public class TimeTrackStatisticController {

    private final TimeTrackStatisticService statisticService;

    @GetMapping(value = "/time/all/{methodName}", produces = "application/json")
    public ResponseEntity<Long> getAllTimeByMethodName(@PathVariable String methodName) {
        long value = statisticService.getAllTimeByMethodName(methodName);
        return ResponseEntity.ok(value);
    }

    @GetMapping(value = "/time/average/{methodName}", produces = "application/json")
    public ResponseEntity<Long> getAverageTimeByMethodName(@PathVariable String methodName) {
        long value = statisticService.getAverageTimeByMethodName(methodName);
        return ResponseEntity.ok(value);
    }
}
