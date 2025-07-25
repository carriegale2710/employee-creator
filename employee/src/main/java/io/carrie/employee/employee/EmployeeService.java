package io.carrie.employee.employee;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import io.carrie.employee.common.exceptions.EmailAlreadyExistsException;
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

    private void checkEmail(String email, EmployeeRepository employeeRepository) throws EmailAlreadyExistsException {
        if (email != null && employeeRepository.existsByEmail(email)) {
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

    public Employee create(CreateEmployeeDTO dto) throws EmailAlreadyExistsException {
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

    public Employee updateById(Integer id, UpdateEmployeeDTO dto) throws EmailAlreadyExistsException {
        Employee found = findById(id);
        this.updateModelMapper.map(dto, found);
        checkEmailForUpdate(dto.getEmail(), id, employeeRepository);
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
