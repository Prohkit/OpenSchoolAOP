package com.example.openschoolaop.repository;

import com.example.openschoolaop.model.MethodExecutionTimeInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MethodExecutionTimeInfoRepository extends JpaRepository<MethodExecutionTimeInfo, Long> {
    List<MethodExecutionTimeInfo> getMethodExecutionTimeInfosByMethodName(String methodName);

}
