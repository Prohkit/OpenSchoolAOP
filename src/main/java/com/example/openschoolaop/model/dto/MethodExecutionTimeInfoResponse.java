package com.example.openschoolaop.model.dto;

import com.example.openschoolaop.model.MethodExecutionTimeInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MethodExecutionTimeInfoResponse {
    private List<MethodExecutionTimeInfo> infoList;
}
