package com.example.test.models.mappers;

import com.example.test.models.entities.Department;
import org.springframework.stereotype.Component;

@Component
public class DepartmentIdMapper {
    public Long map(Department department) {
        return department == null ? null : department.getId();
    }
}
