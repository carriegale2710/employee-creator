package io.carrie.employee.contract.dtos;

import io.carrie.employee.contract.Contract;
import java.math.BigDecimal;
import lombok.*;

@Data
@Builder
public class ResponseContractDTO {
    private Integer id;
    private Integer employeeId;
    private String employeeFirstName;
    private String employeeLastName;
    private String department;
    private String contractType;
    private BigDecimal salaryAmount;
    private Integer hoursPerWeek;
    private String startDate;
    private String endDate;
    private Boolean isActive;

    public static ResponseContractDTO fromEntity(Contract contract) {
        return ResponseContractDTO.builder()
                .id(contract.getId())
                .employeeId(contract.getEmployee().getId()) // Only ID, not full Employee object
                .employeeFirstName(contract.getEmployee().getFirstName())
                .employeeLastName(contract.getEmployee().getLastName())
                .department(contract.getDepartment().name())
                .contractType(contract.getContractType().name())
                .salaryAmount(contract.getSalaryAmount())
                .hoursPerWeek(contract.getHoursPerWeek())
                .startDate(contract.getStartDate() != null ? contract.getStartDate().toString() : null)
                .endDate(contract.getEndDate() != null ? contract.getEndDate().toString() : null)
                .isActive(contract.isActive())
                .build();
    }
}