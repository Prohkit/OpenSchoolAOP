package com.example.openschoolaop.service;

import com.example.openschoolaop.model.MethodExecutionTimeInfo;
import com.example.openschoolaop.repository.MethodExecutionTimeInfoRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@AllArgsConstructor
@Transactional
public class TimeTrackLoggingService {

    private final MethodExecutionTimeInfoRepository timeInfoRepository;

    @Async
    public void saveExecutionTime(String methodName, Long duration) {
        MethodExecutionTimeInfo timeInfo = new MethodExecutionTimeInfo(methodName, duration);
        timeInfoRepository.save(timeInfo);
    }
}
