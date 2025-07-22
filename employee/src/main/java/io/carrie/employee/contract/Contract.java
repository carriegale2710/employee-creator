package io.carrie.employee.contract;

import java.math.BigDecimal;
import java.time.LocalDate;

import io.carrie.employee.employee.Employee;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "contracts")
public class Contract {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne // relationship to employees
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    public enum Department {
        // saved as index in DB
        ENGINEERING,
        SALES,
        DESIGN,
        MARKETING,
        WHOLESALE
    }

    @Column
    @Enumerated
    private Department department; // TODO - create Department entity

    public enum ContractType {
        // saved as index in DB
        FULL_TIME,
        PART_TIME,
        CASUAL,
        CONTRACT
    }

    @Column
    @Enumerated
    private ContractType contractType;

    @Column
    private BigDecimal salaryAmount;

    @Column
    private Integer hoursPerWeek;

    @Column
    private LocalDate startDate;

    @Column
    private LocalDate endDate;

    public Contract() {

    }

    public Contract(Employee employee, Department department, ContractType contractType, BigDecimal salaryAmount,
            Integer hoursPerWeek, LocalDate startDate, LocalDate endDate) {
        this.employee = employee;
        this.department = department;
        this.contractType = contractType;
        this.salaryAmount = salaryAmount;
        this.hoursPerWeek = hoursPerWeek;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    // virtual field indicating if the contract is currently active
    public boolean isActive() {
        return this.endDate == null || this.endDate.isAfter(LocalDate.now());
    }

    public Integer getId() {
        return id;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public ContractType getContractType() {
        return contractType;
    }

    public void setContractType(ContractType contractType) {
        this.contractType = contractType;
    }

    public BigDecimal getSalaryAmount() {
        return salaryAmount;
    }

    public void setSalaryAmount(BigDecimal salaryAmount) {
        this.salaryAmount = salaryAmount;
    }

    public Integer getHoursPerWeek() {
        return hoursPerWeek;
    }

    public void setHoursPerWeek(Integer hoursPerWeek) {
        this.hoursPerWeek = hoursPerWeek;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

}