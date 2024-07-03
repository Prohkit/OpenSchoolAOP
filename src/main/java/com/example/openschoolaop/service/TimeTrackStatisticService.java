package com.example.openschoolaop.service;

import com.example.openschoolaop.model.MethodExecutionTimeInfo;
import com.example.openschoolaop.model.dto.GetExecutionTimeStatsRequest;
import com.example.openschoolaop.repository.MethodExecutionTimeInfoRepository;
import jakarta.persistence.criteria.Predicate;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.OptionalDouble;

@Service
@Transactional
@AllArgsConstructor
public class TimeTrackStatisticService {

    private final MethodExecutionTimeInfoRepository timeInfoRepository;

    public Long getAllTimeByClassNameByInterval(GetExecutionTimeStatsRequest timeStatsRequest) {
        return getMethodExecutionTimeInfoByInterval(
                timeStatsRequest.getClassName(),
                null,
                timeStatsRequest.getFrom(),
                timeStatsRequest.getTo())
                .stream().mapToLong(MethodExecutionTimeInfo::getDuration).sum();
    }

    public Long getAllTimeByClassNameAndMethodNameByInterval(GetExecutionTimeStatsRequest timeStatsRequest) {
        return getMethodExecutionTimeInfoByInterval(
                timeStatsRequest.getClassName(),
                timeStatsRequest.getMethodName(),
                timeStatsRequest.getFrom(),
                timeStatsRequest.getTo())
                .stream().mapToLong(MethodExecutionTimeInfo::getDuration).sum();
    }

    public Long getAverageTimeByClassNameByInterval(GetExecutionTimeStatsRequest timeStatsRequest) {
        OptionalDouble optionalDouble = getMethodExecutionTimeInfoByInterval(
                timeStatsRequest.getClassName(),
                null,
                timeStatsRequest.getFrom(),
                timeStatsRequest.getTo())
                .stream().mapToLong(MethodExecutionTimeInfo::getDuration).average();
        if (optionalDouble.isPresent()) {
            return (long) optionalDouble.getAsDouble();
        }
        return 0L;
    }

    public Long getAverageTimeByClassNameAndMethodNameByInterval(GetExecutionTimeStatsRequest timeStatsRequest) {
        OptionalDouble optionalDouble = getMethodExecutionTimeInfoByInterval(
                timeStatsRequest.getClassName(),
                timeStatsRequest.getMethodName(),
                timeStatsRequest.getFrom(),
                timeStatsRequest.getTo())
                .stream().mapToLong(MethodExecutionTimeInfo::getDuration).average();
        if (optionalDouble.isPresent()) {
            return (long) optionalDouble.getAsDouble();
        }
        return 0L;
    }

    public List<MethodExecutionTimeInfo> getMethodExecutionTimeInfoByInterval
            (String className,
             String methodName,
             LocalDateTime from,
             LocalDateTime to) {
        if (className == null && methodName == null && from == null && to == null) {
            return new ArrayList<>();
        }
        return timeInfoRepository.findAll((root, query, cb) -> {
            Predicate conjunction = cb.conjunction();
            if (Objects.nonNull(className)) {
                Predicate like = cb.like(cb.upper(root.get("className")), "%" + className.toUpperCase() + "%");
                conjunction = cb.and(conjunction, like);
            }
            if (Objects.nonNull(methodName)) {
                Predicate like = cb.like(cb.upper(root.get("methodName")), "%" + methodName.toUpperCase() + "%");
                conjunction = cb.and(conjunction, like);
            }
            if (Objects.nonNull(from)) {
                Predicate greaterThanOrEqualTo = cb.greaterThanOrEqualTo(root.get("startedAt"), cb.literal(from));
                conjunction = cb.and(conjunction, greaterThanOrEqualTo);
            }
            if (Objects.nonNull(to)) {
                Predicate lessThanOrEqualTo = cb.lessThanOrEqualTo(root.get("finishedAt"), cb.literal(to));
                conjunction = cb.and(conjunction, lessThanOrEqualTo);
            }
            return conjunction;
        });
    }
}
