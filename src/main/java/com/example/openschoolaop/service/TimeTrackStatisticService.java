package com.example.openschoolaop.service;

import com.example.openschoolaop.model.MethodExecutionTimeInfo;
import com.example.openschoolaop.repository.MethodExecutionTimeInfoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.OptionalDouble;

@Service
@Transactional
@AllArgsConstructor
public class TimeTrackStatisticService {

    private final MethodExecutionTimeInfoRepository timeInfoRepository;

    public Long getAllTimeByMethodName(String methodName) {
        return timeInfoRepository.getMethodExecutionTimeInfosByMethodName(methodName)
                .stream().mapToLong(MethodExecutionTimeInfo::getDuration).sum();
    }

    public Long getAverageTimeByMethodName(String methodName) {
        OptionalDouble optionalDouble = timeInfoRepository.getMethodExecutionTimeInfosByMethodName(methodName)
                .stream().mapToLong(MethodExecutionTimeInfo::getDuration).average();
        if (optionalDouble.isPresent()) {
            return (long) optionalDouble.getAsDouble();
        }
        return 0L;
    }
}
