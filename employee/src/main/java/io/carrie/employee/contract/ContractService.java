package io.carrie.employee.contract;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import io.carrie.employee.common.exceptions.*;
import io.carrie.employee.contract.dtos.*;
import io.carrie.employee.employee.Employee;
import io.carrie.employee.employee.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;

@Slf4j
@Service
@RequiredArgsConstructor
public class ContractService {

    private final EmployeeService employeeService;
    private final ContractRepository contractRepository;
    private final ModelMapper modelMapper;

    public Contract create(CreateContractDTO dto) {
        Employee employee = employeeService.findById(dto.getEmployeeId());
        log.debug("Creating contract for employee: {} {}", employee.getFirstName(), employee.getLastName());

        Contract created = modelMapper.map(dto, Contract.class);
        if (created.getEndDate() != null && created.getStartDate().isAfter(created.getEndDate())) {
            throw new IllegalArgumentException("Start date cannot be after end date");
        }

        created.setEmployee(employee);
        Contract saved = this.contractRepository.save(created);
        log.debug("Successfully saved new contract: {}", saved);
        return saved;
    }

    public boolean deleteContractById(Integer id) {
        this.findById(id);
        this.contractRepository.deleteById(id);
        return true;
    }

    public Contract findById(Integer id) throws NotFoundException {
        Optional<Contract> result = this.contractRepository.findById(id);
        if (result.isEmpty())
            throw new NotFoundException("Contract with id " + id + " does not exist");
        log.debug("Found contract: {}", result.get());
        return result.get();
    }

    public List<Contract> findAll() {
        List<Contract> contracts = this.contractRepository.findAll();
        return contracts;
    }

}
