package io.carrie.employee.employee;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import io.carrie.employee.common.exceptions.NotFoundException;
import io.carrie.employee.employee.dtos.CreateEmployeeDTO;
import io.carrie.employee.employee.dtos.UpdateEmployeeDTO;

@Service
public class EmployeeService {

    private EmployeeRepository employeeRepository;
    private ModelMapper modelMapper;
    private ModelMapper updateModelMapper;

    EmployeeService(EmployeeRepository employeeRepository, ModelMapper modelMapper, ModelMapper updateModelMapper) {
        this.employeeRepository = employeeRepository;
        this.modelMapper = modelMapper;
        this.updateModelMapper = updateModelMapper;
    }

    public List<Employee> findAll() {
        return this.employeeRepository.findAll();
    }

    public Employee create(CreateEmployeeDTO dto) throws IllegalArgumentException {
        Employee created = modelMapper.map(dto, Employee.class);
        checkEmail(created.getEmail(), employeeRepository);
        return this.employeeRepository.save(created);
    }

    public boolean deleteById(Integer id) {
        this.findById(id);
        this.employeeRepository.deleteById(id);
        return true;
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
            throw new NotFoundException("Employee with id " + id + " does not exist");
        }
        return result.get();
    }

    static void checkEmail(String email, EmployeeRepository employeeRepository) {
        if (email != null && employeeRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Employee with email already exists");
        }
    }

}
