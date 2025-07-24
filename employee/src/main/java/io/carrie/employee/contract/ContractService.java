package io.carrie.employee.contract;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import io.carrie.employee.common.exceptions.NotFoundException;
import io.carrie.employee.contract.dtos.CreateContractDTO;
import io.carrie.employee.employee.Employee;
import io.carrie.employee.employee.EmployeeService;

@Service
public class ContractService {

    private final EmployeeService employeeService;
    private ContractRepository contractRepository;
    private ModelMapper modelMapper;

    public ContractService(ContractRepository contractRepository, EmployeeService employeeService,
            ModelMapper modelMapper) {
        this.contractRepository = contractRepository;
        this.employeeService = employeeService;
        this.modelMapper = modelMapper;
    }

    public Contract create(CreateContractDTO dto) {
        Employee employee = employeeService.findById(dto.getEmployeeId());
        Contract created = modelMapper.map(dto, Contract.class);
        created.setEmployee(employee);
        return this.contractRepository.save(created);
    }

    public boolean deleteById(Integer id) {
        this.findById(id);
        this.contractRepository.deleteById(id);
        return true;
    }

    public Contract findById(Integer id) throws NotFoundException {
        Optional<Contract> result = this.contractRepository.findById(id);
        if (result.isEmpty()) {
            throw new NotFoundException("Contract with id " + id + " does not exist");
        }
        return result.get();
    }

    public List<Contract> findAll() {
        return this.contractRepository.findAll();
    }

}
