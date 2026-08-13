package com.example.test.controllers;

import com.example.test.models.dto.req.DepartmentReq;
import com.example.test.models.dto.wrapper.ApiResponse;
import com.example.test.models.services.department.IDepartmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/departments")
@RequiredArgsConstructor
public class DepartmentController {
    private final IDepartmentService departmentService;
    @PostMapping
    public ResponseEntity<?> addNewDepartment(@Valid @ModelAttribute DepartmentReq req) {
        log.info("Received request to add new department: {}", req);
        return ResponseEntity.status(201).body(
                ApiResponse.builder()
                        .message("Add New Department Successfully")
                        .code(201)
                        .data(departmentService.createDepartment(req))
                        .build()
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateDepartment(@PathVariable Long id, @Valid @ModelAttribute DepartmentReq req){
        log.info("Updating department with ID: {}", id);
        return ResponseEntity.status(200).body(
                ApiResponse.builder()
                        .message("Updated Department Successfully")
                        .code(200)
                        .data(departmentService.updateDepartment(id, req))
                        .build()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        log.info("Fetching department with ID: {}", id);
        return ResponseEntity.ok(
                ApiResponse.builder()
                        .message("Get Department Successfully")
                        .code(200)
                        .data(departmentService.findById(id))
                        .build()
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> dropout(@PathVariable Long id){
        departmentService.deleteDepartment(id);
        log.info("Deleted department with ID: {}", id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(
                ApiResponse.builder()
                        .message("Deleted Department Successfully")
                        .code(204)
                        .data(null)
                        .build()
        );
    }

    @GetMapping
    public ResponseEntity<?> findAll() {
        log.info("Fetching all departments");
        return ResponseEntity.ok(
                ApiResponse.builder()
                        .message("Get Department Successfully")
                        .code(200)
                        .data(departmentService.findAll())
                        .build()
        );
    }

}
