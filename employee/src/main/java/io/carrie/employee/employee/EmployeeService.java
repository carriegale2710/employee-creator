package io.carrie.employee.employee;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import io.carrie.employee.contract.ContractRepository;
import io.carrie.employee.employee.dtos.*;
import io.carrie.employee.common.exceptions.*;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final ModelMapper modelMapper;
    private final ModelMapper updateModelMapper;
    private final ContractRepository contractRepository;

    public EmployeeService(EmployeeRepository employeeRepository, ModelMapper modelMapper,
            ModelMapper updateModelMapper,
            ContractRepository contractRepository) {
        this.employeeRepository = employeeRepository;
        this.modelMapper = modelMapper;
        this.updateModelMapper = updateModelMapper;
        this.contractRepository = contractRepository;
    }

    public List<Employee> findAll() {
        List<Employee> employees = this.employeeRepository.findAll();
        log.debug("Retrieved {} employees from database", employees.size());
        return employees;
    }

    public Employee create(CreateEmployeeDTO dto) throws EmailAlreadyExistsException {
        Employee created = modelMapper.map(dto, Employee.class);
        checkEmail(created.getEmail(), employeeRepository);
        Employee saved = this.employeeRepository.save(created);
        log.info("Employee saved to database with ID: {}", saved.getId());
        return saved;
    }

    @Transactional // so we can delete contracts for this employee
    public void deleteEmployeeById(Integer id) {
        findById(id);
        log.debug("Deleting contracts for employee ID: {}", id);
        contractRepository.deleteAllByEmployeeId(id); // delete all contracts first
        log.debug("Contracts deleted for employee ID: {}", id);
        this.employeeRepository.deleteById(id); // safely delete the employee
        log.info("Employee and associated contracts deleted from database for ID: {}", id);
    }

    public Employee updateById(Integer id, UpdateEmployeeDTO dto) throws EmailAlreadyExistsException {
        Employee found = findById(id);
        this.updateModelMapper.map(dto, found);
        checkEmailForUpdate(dto.getEmail(), id, employeeRepository);
        Employee updated = this.employeeRepository.save(found);
        log.info("Employee updated in database with ID: {}", updated.getId());
        return updated;
    }

    public Employee findById(Integer id) throws NotFoundException {
        Optional<Employee> result = this.employeeRepository.findById(id);
        if (result.isEmpty()) {
            log.warn("Employee not found with ID: {}", id);
            throw new NotFoundException(String.format("Employee with id %d does not exist", id));
        }
        return result.get();
    }

    // NOTE - Custom validation methods for email uniqueness
    private void checkEmail(String email, EmployeeRepository employeeRepository) throws EmailAlreadyExistsException {
        if (email != null && employeeRepository.existsByEmail(email)) {
            log.warn("Email validation failed - Email already exists: {}", email);
            throw new EmailAlreadyExistsException(String.format("Employee with email '%s' already exists", email));
        }
    }

    private void checkEmailForUpdate(String email, Integer currentEmployeeId, EmployeeRepository employeeRepository)
            throws EmailAlreadyExistsException {
        if (email != null && employeeRepository.existsByEmail(email)) {
            // Check if the email belongs to a different employee
            Employee existingEmployee = employeeRepository.findByEmail(email);
            if (existingEmployee != null && !existingEmployee.getId().equals(currentEmployeeId)) {
                throw new EmailAlreadyExistsException("Employee with email already exists");
            }
        }
    }

}
