package com.example.openschoolaop.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class GetExecutionTimeStatsRequest {

    private String className;

    private String methodName;

    private LocalDateTime from;

    private LocalDateTime to;
}
