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

    public Employee create(CreateEmployeeDTO data) {
        // turn CreateEmployeeDTO into a Employee object
        // can use modelmapper instead of using setters for each property
        Employee newEmployee = modelMapper.map(data, Employee.class);
        Employee savedEmployee = this.employeeRepository.save(newEmployee);
        return savedEmployee;
    }

    public boolean deleteById(Integer id) {
        // does the book exist?
        Optional<Employee> foundEmployee = this.findById(id);
        // if yes, delete and return true
        if (foundEmployee.isPresent()) {
            this.employeeRepository.delete(foundEmployee.get());
            return true;
        }
        // if no, return false
        return false;
    }

}
