package io.carrie.employee.contract.dtos;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CreateContractDTO {

    @NotNull(message = "Employee ID is required")
    private Integer employeeId;

    private String department;

    @NotBlank(message = "Contract type is required")
    private String contractType;

    @DecimalMin(value = "0.0", inclusive = false, message = "Salary must be a positive number")
    private double salaryAmount;
    // instead of double for money for rounding

    @Max(value = 50, message = "Hours per week can't exceed 50")
    private Integer hoursPerWeek;

    // Convert local dates from ISO string date

    @NotNull(message = "Start date is required")
    @DateTimeFormat
    private String startDate;

    @DateTimeFormat
    // @ValidDateRange //TODO - check endDate is not before startDate
    private String endDate;

    public CreateContractDTO() {

    }

    public CreateContractDTO(Integer employeeId, String department, String contractType, double salaryAmount,
            Integer hoursPerWeek, String startDate, String endDate) {
        this.employeeId = employeeId;
        this.department = department;
        this.contractType = contractType;
        this.salaryAmount = salaryAmount;
        this.hoursPerWeek = hoursPerWeek;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Integer getEmployeeId() {
        return employeeId;
    }

    public String getDepartment() {
        return department;
    }

    public String getContractType() {
        return contractType;
    }

    public double getSalaryAmount() {
        return salaryAmount;
    }

    public Integer getHoursPerWeek() {
        return hoursPerWeek;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

}
