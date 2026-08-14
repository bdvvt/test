package com.example.test.models.mappers;

import com.example.test.models.dto.req.AuditLogData;
import com.example.test.models.entities.ApplicationLog;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ApplicationLogMapper {
    @Mapping(target = "id", ignore = true)
    ApplicationLog toEntity(AuditLogData data);
}
