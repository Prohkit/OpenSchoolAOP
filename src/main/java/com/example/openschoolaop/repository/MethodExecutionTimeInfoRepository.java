package com.example.openschoolaop.repository;

import com.example.openschoolaop.model.MethodExecutionTimeInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface MethodExecutionTimeInfoRepository extends JpaRepository<MethodExecutionTimeInfo, Long>,
        JpaSpecificationExecutor<MethodExecutionTimeInfo> {
}
