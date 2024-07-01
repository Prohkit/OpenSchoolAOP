package com.example.openschoolaop.aspect;

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
            Instant start = Instant.now();
            Object object = joinPoint.proceed();
            Instant finish = Instant.now();
            long timeElapsed = Duration.between(start, finish).toMillis();
            timeTrackLoggingService.saveExecutionTime(methodName, timeElapsed);
            return object;
        } catch (Throwable e) {
            log.error("Ошибка trackTimeAspect ", e);
        }
        return null;
    }
}
