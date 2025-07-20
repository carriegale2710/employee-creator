package io.carrie.employee.employee;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import io.carrie.employee.common.exceptions.NotFoundException;

@Service
public class EmployeeService {

    private EmployeeRepository employeeRepository;
    private ModelMapper modelMapper;

    EmployeeService(EmployeeRepository employeeRepository, ModelMapper modelMapper) {
        this.employeeRepository = employeeRepository;
        this.modelMapper = modelMapper;
    }

    public List<Employee> findAll() {
        return this.employeeRepository.findAll();
    }

    public Employee findById(Integer id) throws NotFoundException {
        Optional<Employee> result = this.employeeRepository.findById(id);
        if (result.isEmpty()) {
            throw new NotFoundException("Employee with id " + id + " does not exist");
        }
        return result.get();

    }

    public Employee create(CreateEmployeeDTO data) throws IllegalArgumentException {
        // turn CreateEmployeeDTO into a Employee object
        Employee newEmployee = modelMapper.map(data, Employee.class);
        String email = newEmployee.getEmail();
        if (this.employeeRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Employee with email already exists");
            // Throw exceptions in the service class. Handle them in the controller.
        }
        Employee savedEmployee = this.employeeRepository.save(newEmployee);
        return savedEmployee;
    }

    public boolean deleteById(Integer id) {
        this.findById(id);
        this.employeeRepository.deleteById(id);
        return true;
    }

}
