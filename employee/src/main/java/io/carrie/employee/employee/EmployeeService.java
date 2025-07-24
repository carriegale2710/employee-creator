package io.carrie.employee.employee;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import io.carrie.employee.common.exceptions.NotFoundException;
import io.carrie.employee.contract.ContractRepository;
import io.carrie.employee.employee.dtos.CreateEmployeeDTO;
import io.carrie.employee.employee.dtos.UpdateEmployeeDTO;

@Service
public class EmployeeService {

    private EmployeeRepository employeeRepository;
    private ModelMapper modelMapper;
    private ModelMapper updateModelMapper;
    private ContractRepository contractRepository;

    private void checkEmail(String email, EmployeeRepository employeeRepository) throws IllegalArgumentException {
        if (email != null && employeeRepository.existsByEmail(email)) {
            throw new IllegalArgumentException(String.format("Employee with email '%s' already exists", email));
        }
    }

    EmployeeService(EmployeeRepository employeeRepository, ModelMapper modelMapper, ModelMapper updateModelMapper,
            ContractRepository contractRepository) {
        this.employeeRepository = employeeRepository;
        this.modelMapper = modelMapper;
        this.updateModelMapper = updateModelMapper;
        this.contractRepository = contractRepository;
    }

    public List<Employee> findAll() {
        return this.employeeRepository.findAll();
    }

    public Employee create(CreateEmployeeDTO dto) {
        Employee created = modelMapper.map(dto, Employee.class);
        checkEmail(created.getEmail(), employeeRepository);
        return this.employeeRepository.save(created);
    }

    @Transactional // so we can delete contracts for this employee
    public void deleteEmployeeById(Integer id) {
        findById(id); // Check if employee exists
        contractRepository.deleteAllByEmployeeId(id); // delete all contracts
        this.employeeRepository.deleteById(id); // safely delete the employee
    }

    public Employee updateById(Integer id, UpdateEmployeeDTO dto) {
        Employee found = findById(id);
        this.updateModelMapper.map(dto, found);
        checkEmail(dto.getEmail(), employeeRepository);
        return this.employeeRepository.save(found);
    }

    public Employee findById(Integer id) throws NotFoundException {
        Optional<Employee> result = this.employeeRepository.findById(id);
        if (result.isEmpty()) {
            throw new NotFoundException(String.format("Employee with id %d does not exist", id));
        }
        return result.get();
    }

}
