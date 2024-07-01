package com.example.openschoolaop.mapper;

import com.example.openschoolaop.model.MethodExecutionTimeInfo;
import com.example.openschoolaop.model.dto.AddMethodExecutionTimeInfoDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MethodExecutionTimeInfoMapper {

    MethodExecutionTimeInfo toEntity(AddMethodExecutionTimeInfoDto dto);
}
