package io.carrie.employee.contract;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import io.carrie.employee.common.exceptions.*;
import io.carrie.employee.contract.dtos.*;
import io.carrie.employee.contract.enums.ContractType;
import io.carrie.employee.contract.enums.Department;
import io.carrie.employee.employee.Employee;
import io.carrie.employee.employee.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;

@Slf4j
@Service
@RequiredArgsConstructor // replaces constructor
public class ContractService {

    private final EmployeeService employeeService;
    private final ContractRepository contractRepository;

    public List<Contract> findAll() {
        List<Contract> contracts = this.contractRepository.findAll();
        return contracts;
    }

    public Contract findById(Integer id) throws NotFoundException {
        Optional<Contract> result = this.contractRepository.findById(id);
        if (result.isEmpty())
            throw new NotFoundException("Contract with id " + id + " does not exist");
        log.debug("Found contract: {}", result.get());
        return result.get();
    }

    public boolean deleteById(Integer id) {
        this.findById(id);
        this.contractRepository.deleteById(id);
        return true;
    }

    public Contract create(CreateContractDTO dto) {

        validateDto(dto);
        Employee foundEmployee = employeeService.findById(dto.getEmployeeId());

        // Manual mapping (more explicit and safe for enums and complex types)
        Contract created = Contract.builder()
                .department(Department.valueOf(dto.getDepartment()))
                .contractType(ContractType.valueOf(dto.getContractType()))
                .salaryAmount(dto.getSalaryAmount())
                .hoursPerWeek(dto.getHoursPerWeek())
                .startDate(LocalDate.parse(dto.getStartDate()))
                .endDate(dto.getEndDate() != null ? LocalDate.parse(dto.getEndDate()) : null)
                .employee(foundEmployee)
                .build();

        return contractRepository.save(created);
    }

    // SECTION helper methods

    public void validateDto(CreateContractDTO dto) {

        if (dto.getDepartment() == null || dto.getContractType() == null) {
            throw new IllegalArgumentException("Department and ContractType must not be null");
        }
        // (if entered) check that end date is not before start date
        if (dto.getEndDate() == null) {
            return;
        }
        LocalDate startDate = LocalDate.parse(dto.getStartDate());
        LocalDate endDate = LocalDate.parse(dto.getEndDate());

        if (endDate.isBefore(startDate)) {
            throw new IllegalArgumentException(
                    "End date cannot be before start date");
        }
        log.debug("DTO validation passed");
    }

}
