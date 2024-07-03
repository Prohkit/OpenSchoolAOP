package com.example.openschoolaop.aspect;

import com.example.openschoolaop.exception.TrackTimeException;
import com.example.openschoolaop.model.dto.AddMethodExecutionTimeInfoDto;
import com.example.openschoolaop.service.TimeTrackLoggingService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Aspect
@Component
public class TimeTrackLoggingAspect {

    private final TimeTrackLoggingService timeTrackLoggingService;

    @Value("${asynchronous.enabled}")
    private boolean asynchronousEnabled;

    public TimeTrackLoggingAspect(TimeTrackLoggingService timeTrackLoggingService) {
        this.timeTrackLoggingService = timeTrackLoggingService;
    }

    @Pointcut("@annotation(com.example.openschoolaop.annotation.TrackTime)")
    public void trackTimePointcut() {
    }

    @Pointcut("@annotation(com.example.openschoolaop.annotation.TrackAsyncTime)")
    public void trackAsyncTimePointcut() {
    }

    @Around("trackTimePointcut()")
    public Object trackTimeAdvice(ProceedingJoinPoint joinPoint) {
        return trackTime(joinPoint);
    }

    @Around("trackAsyncTimePointcut()")
    public Object trackAsyncTimeAdvice(ProceedingJoinPoint joinPoint) {
        if (asynchronousEnabled) {
            CompletableFuture<Object> future = CompletableFuture.supplyAsync(() -> trackTime(joinPoint));
            return future.join();
        } else {
            return trackTime(joinPoint);
        }
    }

    private Object trackTime(ProceedingJoinPoint joinPoint) {
        try {
            String methodName = joinPoint.getSignature().getName();
            String className = joinPoint.getSignature().getDeclaringType().getSimpleName();

            Instant start = Instant.now();
            Object object = joinPoint.proceed();
            Instant finish = Instant.now();

            long timeElapsed = Duration.between(start, finish).toMillis();
            LocalDateTime startedAt = LocalDateTime.ofInstant(start, ZoneOffset.UTC);
            LocalDateTime finishedAt = LocalDateTime.ofInstant(finish, ZoneOffset.UTC);
            AddMethodExecutionTimeInfoDto timeInfoDto =
                    new AddMethodExecutionTimeInfoDto(className, methodName, timeElapsed, startedAt, finishedAt);
            timeTrackLoggingService.saveExecutionTime(timeInfoDto);
            return object;
        } catch (Throwable e) {
            throw new TrackTimeException("Error in TrackTimeLoggingAspect class, trackTime method");
        }
    }
}
