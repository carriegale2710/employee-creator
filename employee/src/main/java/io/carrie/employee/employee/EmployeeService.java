package io.carrie.employee.employee;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

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

    public Optional<Employee> findById(Integer id) {
        return this.employeeRepository.findById(id);
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
        Optional<Employee> foundEmployee = this.findById(id); // does the book exist?
        if (foundEmployee.isEmpty()) {
            return false;
        }
        this.employeeRepository.deleteById(id);
        return true;
    }

}
