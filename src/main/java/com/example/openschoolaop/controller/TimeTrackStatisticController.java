package com.example.openschoolaop.controller;

import com.example.openschoolaop.model.MethodExecutionTimeInfo;
import com.example.openschoolaop.model.dto.GetExecutionTimeStatsRequest;
import com.example.openschoolaop.model.dto.MethodExecutionTimeInfoResponse;
import com.example.openschoolaop.service.TimeTrackStatisticService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("stats")
public class TimeTrackStatisticController {

    private final TimeTrackStatisticService statisticService;

    @GetMapping(value = "/time/class/all", produces = "application/json")
    public ResponseEntity<Long> getAllTimeByClassNameByInterval(@RequestBody GetExecutionTimeStatsRequest statsRequest) {
        long value = statisticService.getAllTimeByClassNameByInterval(statsRequest);
        return ResponseEntity.ok(value);
    }

    @GetMapping(value = "/time/all", produces = "application/json")
    public ResponseEntity<Long> getAllTimeByClassNameAndMethodNameByInterval(@RequestBody GetExecutionTimeStatsRequest statsRequest) {
        long value = statisticService.getAllTimeByClassNameAndMethodNameByInterval(statsRequest);
        return ResponseEntity.ok(value);
    }

    @GetMapping(value = "/time/class/average", produces = "application/json")
    public ResponseEntity<Long> getAverageTimeByClassNameByInterval(@RequestBody GetExecutionTimeStatsRequest statsRequest) {
        long value = statisticService.getAverageTimeByClassNameByInterval(statsRequest);
        return ResponseEntity.ok(value);
    }

    @GetMapping(value = "/time/average", produces = "application/json")
    public ResponseEntity<Long> getAverageTimeByClassNameAndMethodNameByInterval(@RequestBody GetExecutionTimeStatsRequest statsRequest) {
        long value = statisticService.getAverageTimeByClassNameAndMethodNameByInterval(statsRequest);
        return ResponseEntity.ok(value);
    }

    @GetMapping(value = "/info", produces = "application/json")
    public ResponseEntity<MethodExecutionTimeInfoResponse> getMethodExecutionTimeInfoByInterval(@RequestBody GetExecutionTimeStatsRequest statsRequest) {
        List<MethodExecutionTimeInfo> timeInfos = statisticService
                .getMethodExecutionTimeInfoByInterval(
                        statsRequest.getClassName(),
                        statsRequest.getMethodName(),
                        statsRequest.getFrom(),
                        statsRequest.getTo());
        MethodExecutionTimeInfoResponse timeInfoResponse = new MethodExecutionTimeInfoResponse(timeInfos);
        return ResponseEntity.ok(timeInfoResponse);
    }
}
