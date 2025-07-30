package io.carrie.employee.department;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.carrie.employee.department.dtos.CreateDepartmentDTO;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Slf4j
@RestController
@RequestMapping("/departments")
public class DepartmentController {

    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
        log.info("DepartmentController initialized");
    }

    @PostMapping
    public ResponseEntity<Department> postMethodName(@Valid @RequestBody CreateDepartmentDTO dto) {
        Department created = departmentService.createDepartment(dto);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Department>> getAllDepartments() {
        List<Department> departments = departmentService.getAllDepartments();
        return ResponseEntity.ok(departments);
    }

    @GetMapping("/{name}")
    public ResponseEntity<Department> getByName(@PathVariable String name) {
        Department found = departmentService.findByName(name);
        return ResponseEntity.ok(found);
    }

}
