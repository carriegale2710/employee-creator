package io.carrie.employee.contract;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.carrie.employee.common.exceptions.NotFoundException;
import io.carrie.employee.contract.dtos.CreateContractDTO;
import io.carrie.employee.employee.Employee;
import io.carrie.employee.employee.EmployeeService;

@Service
public class ContractService {

    private static final Logger logger = LoggerFactory.getLogger(ContractService.class);
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
        logger.debug("Creating contract for employee: {} {}", employee.getFirstName(), employee.getLastName());

        Contract created = modelMapper.map(dto, Contract.class);
        created.setEmployee(employee);
        Contract saved = this.contractRepository.save(created);
        logger.info("Contract saved to database with ID: {}", saved.getId());
        return saved;
    }

    public boolean deleteContractById(Integer id) {
        this.findById(id);
        this.contractRepository.deleteById(id);
        logger.info("Contract deleted from database with ID: {}", id);
        return true;
    }

    public Contract findById(Integer id) throws NotFoundException {
        Optional<Contract> result = this.contractRepository.findById(id);
        if (result.isEmpty()) {
            logger.warn("Contract not found with ID: {}", id);
            throw new NotFoundException("Contract with id " + id + " does not exist");
        }
        return result.get();
    }

    public List<Contract> findAll() {
        List<Contract> contracts = this.contractRepository.findAll();
        logger.debug("Retrieved {} contracts from database", contracts.size());
        return contracts;
    }

}
