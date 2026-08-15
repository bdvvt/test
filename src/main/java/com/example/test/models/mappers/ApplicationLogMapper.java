package com.example.test.models.mappers;

import com.example.test.models.dto.req.AuditLogData;
import com.example.test.models.entities.Log;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ApplicationLogMapper {
    @Mapping(target = "id", ignore = true)
    Log toEntity(AuditLogData data);
}
