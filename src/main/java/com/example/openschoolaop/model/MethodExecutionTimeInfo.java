package com.example.openschoolaop.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.OffsetDateTime;

@Data
@NoArgsConstructor
@Entity
@Table(name = "method_execution_time_log")
public class MethodExecutionTimeInfo {
    public MethodExecutionTimeInfo(String methodName, Long duration) {
        this.methodName = methodName;
        this.duration = duration;
    }

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "method_name")
    private String methodName;

    @Column(name = "duration")
    private Long duration;

    @CreationTimestamp
    @Column(name = "created_at")
    private OffsetDateTime createdAt;
}
