package com.example.openschoolaop.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
@Table(name = "method_execution_time_log")
public class MethodExecutionTimeInfo {
    public MethodExecutionTimeInfo(
            String methodName,
            String className,
            Long duration,
            LocalDateTime startedAt,
            LocalDateTime finishedAt
    ) {
        this.methodName = methodName;
        this.className = className;
        this.duration = duration;
        this.startedAt = startedAt;
        this.finishedAt = finishedAt;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "class_name")
    private String className;

    @Column(name = "method_name")
    private String methodName;

    @Column(name = "duration")
    private Long duration;

    @Column(name = "started_at")
    private LocalDateTime startedAt;

    @Column(name = "finished_at")
    private LocalDateTime finishedAt;
}
