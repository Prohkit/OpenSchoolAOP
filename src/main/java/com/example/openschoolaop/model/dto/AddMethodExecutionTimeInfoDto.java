package com.example.openschoolaop.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class AddMethodExecutionTimeInfoDto {

    private String className;

    private String methodName;

    private Long duration;

    private LocalDateTime startedAt;

    private LocalDateTime finishedAt;
}
