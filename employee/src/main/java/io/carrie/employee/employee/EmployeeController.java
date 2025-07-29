package io.carrie.employee.employee;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.carrie.employee.employee.dtos.CreateEmployeeDTO;
import io.carrie.employee.employee.dtos.UpdateEmployeeDTO;
import jakarta.validation.Valid;

// What to log: Request/Responses
// HTTP requests received (endpoints hit, parameters)
// Response status and basic outcomes
// Request validation errors
// Authentication/authorization events

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);
    private EmployeeService employeeService;

    EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
        logger.info("EmployeeController initialized");
    }

    @PostMapping
    public ResponseEntity<Employee> create(@Valid @RequestBody CreateEmployeeDTO dto) {
        logger.info("Creating new employee - First Name: {}, Last Name: {}",
                dto.getFirstName(), dto.getLastName());
        logger.debug("Create employee request DTO: {}", dto);

        try {
            Employee created = this.employeeService.create(dto);
            logger.info("Successfully created employee with ID: {}", created.getId());
            return new ResponseEntity<>(created, HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("Error creating employee: {}", e.getMessage(), e);
            throw e;
        }
    }

    @GetMapping
    public ResponseEntity<List<Employee>> getAll() {
        logger.info("Fetching all employees");

        try {
            List<Employee> list = this.employeeService.findAll();
            logger.info("Successfully retrieved {} employees", list.size());
            return new ResponseEntity<>(list, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error fetching all employees: {}", e.getMessage(), e);
            throw e;
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> getById(@PathVariable Integer id) {
        logger.info("Fetching employee by ID: {}", id);

        try {
            Employee found = this.employeeService.findById(id);
            logger.info("Successfully found employee: {} {}", found.getFirstName(), found.getLastName());
            return new ResponseEntity<>(found, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error fetching employee with ID {}: {}", id, e.getMessage(), e);
            throw e;
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Integer id) {
        logger.info("Deleting employee with ID: {}", id);

        try {
            this.employeeService.deleteEmployeeById(id);
            logger.info("Successfully deleted employee with ID: {}", id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            logger.error("Error deleting employee with ID {}: {}", id, e.getMessage(), e);
            throw e;
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Employee> updateById(@PathVariable Integer id, @Valid @RequestBody UpdateEmployeeDTO dto) {
        logger.info("Updating employee with ID: {}", id);
        logger.debug("Update employee request DTO: {}", dto);

        try {
            Employee edited = this.employeeService.updateById(id, dto);
            logger.info("Successfully updated employee with ID: {} - Name: {} {}",
                    edited.getId(), edited.getFirstName(), edited.getLastName());
            return new ResponseEntity<>(edited, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error updating employee with ID {}: {}", id, e.getMessage(), e);
            throw e;
        }
    }

}
