package io.carrie.employee.contract.dtos;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CreateContractDTO {

    @NotNull(message = "Employee ID is required")
    private Long employeeId;

    @NotNull(message = "Department ID is required")
    private Long departmentId;

    @NotBlank(message = "Contract type is required")
    private String contractType;

    @DecimalMin(value = "0.0", inclusive = false, message = "Salary must be a positive number")
    private BigDecimal salaryAmount;
    // instead of double for money for rounding

    @Max(value = 50, message = "Hours per week can't exceed 50")
    private Integer hoursPerWeek;

    // Convert local dates from ISO string date

    @NotNull(message = "Start date is required")
    @DateTimeFormat
    private String startDate;

    @FutureOrPresent
    @DateTimeFormat
    // @ValidDateRange //TODO - check endDate is not before startDate
    private String endDate;

    public CreateContractDTO() {

    }

}
