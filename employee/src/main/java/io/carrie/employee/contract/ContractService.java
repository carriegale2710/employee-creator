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

        // fetch employee to associate with the contract
        Employee foundEmployee = employeeService.findById(dto.getEmployeeId());

        tidyDto(dto);

        // parse dates and convert to LocalDate
        LocalDate startDate = LocalDate.parse(dto.getStartDate());
        LocalDate endDate = dto.getEndDate() != null ? LocalDate.parse(dto.getEndDate()) : null;

        validateDto(dto, startDate, endDate);

        // Manual mapping (more explicit and safe for enums and complex types)
        Contract created = Contract.builder()
                .department(Department.valueOf(dto.getDepartment()))
                .contractType(ContractType.valueOf(dto.getContractType()))
                .salaryAmount(dto.getSalaryAmount())
                .hoursPerWeek(dto.getHoursPerWeek())
                .startDate(startDate)
                .endDate(endDate)
                .employee(foundEmployee)
                .build();

        System.out.println("Saving contract: " + created);

        return contractRepository.save(created);
    }

    // SECTION helper methods

    public void tidyDto(CreateContractDTO dto) {
        // Tidy up the DTO fields to ensure consistent formatting
        if (dto.getDepartment() != null) {
            dto.setDepartment(dto.getDepartment().trim().replace(" ", "_").toUpperCase());
        }
        if (dto.getContractType() != null) {
            dto.setContractType(dto.getContractType().trim().replace(" ", "_").toUpperCase());
        }
        if (dto.getStartDate() != null) {
            dto.setStartDate(dto.getStartDate().trim());
        }
        if (dto.getEndDate() != null) {
            dto.setEndDate(dto.getEndDate().trim());
        }
        log.debug("DTO tidied: {}", dto);
    }

    public void validateDto(CreateContractDTO dto, LocalDate startDate, LocalDate endDate) {
        if (endDate != null && endDate.isBefore(startDate)) {
            throw new IllegalArgumentException(
                    "End date cannot be before start date");
        }
        log.debug("DTO validation passed");
    }

}
