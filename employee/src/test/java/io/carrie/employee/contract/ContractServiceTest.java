package io.carrie.employee.contract;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import io.carrie.employee.contract.dtos.CreateContractDTO;
import io.carrie.employee.contract.dtos.ResponseContractDTO;
import io.carrie.employee.contract.enums.ContractType;
import io.carrie.employee.contract.enums.Department;
import io.carrie.employee.employee.Employee;
import io.carrie.employee.employee.EmployeeService;

public class ContractServiceTest {

        // NOTE (just testing the service works properly)
        // providing fake implementation for control data comparison
        // the data is not based on dependencies
        // won't work in e2e tests

        @Mock
        private EmployeeService employeeService;

        @Mock
        private ContractRepository contractRepository;

        @Spy // call existing or mock
        @InjectMocks
        private ContractService contractService;

        @BeforeEach // setup your mock data for testing
        public void setup() {
                MockitoAnnotations.openMocks(this);
        }

        @Test
        public void findAll_CallsFindAllOnRepo() {
                this.contractService.findAll();
                verify(this.contractRepository).findAll();
        }

        @Test
        public void findById_CallsFindByIdOnRepo() {
                Contract contract = new Contract();

                when(this.contractRepository.findById(1))
                                .thenReturn(java.util.Optional.of(contract));

                this.contractService.findById(1);
                verify(this.contractRepository).findById(1);
        }

        @Test
        public void deleteById_CallsDeleteByIdOnRepo() {
                doReturn(new Contract())
                                .when(contractService).findById(1);

                this.contractService.deleteById(1);

                verify(this.contractRepository).deleteById(1);
        }

        @Test
        public void create_CallsSaveOnRepo() {
                // Arrange- mock what should return (control vs mock data)
                Employee employee = new Employee();
                employee.setId(1); // Set a valid ID for the employee
                Contract contract = new Contract();
                CreateContractDTO dto = new CreateContractDTO(); // Date strings

                dto.setContractType("FULL_TIME");
                dto.setDepartment("SALES");
                dto.setHoursPerWeek(40);
                dto.setSalaryAmount(new BigDecimal("50000.00"));
                dto.setStartDate("2023-01-01");

                when(this.employeeService
                                .findById(employee.getId()))
                                .thenReturn(employee);

                when(this.contractRepository
                                .save(any(Contract.class)))
                                .thenReturn(contract);

                // Act - call the method
                Contract result = this.contractService.create(dto);
                // Assert - verify save was called
                verify(this.contractRepository).save(any(Contract.class));

                // Assert - check returned contract
                assertNotNull(result);
                assertEquals(contract, result);
                assertEquals(contract, result);
        }

        @Test
        public void tidyDto_CleansUpDtoFields() {
                CreateContractDTO dto = new CreateContractDTO();
                dto.setDepartment(" Sales ");
                dto.setContractType(" Full Time ");
                dto.setStartDate(" 2023-01-01 ");

                contractService.tidyDto(dto);

                assertEquals("SALES", dto.getDepartment());
                assertEquals("FULL_TIME", dto.getContractType());
                assertEquals("2023-01-01", dto.getStartDate().trim());
        }

        @Test
        public void validateDto_ValidatesContractDates() {
                CreateContractDTO dto = new CreateContractDTO();
                dto.setStartDate("2023-01-01");
                dto.setEndDate("2023-12-31");

                contractService.validateDto(dto, LocalDate.parse(dto.getStartDate()),
                                LocalDate.parse(dto.getEndDate()));
        }

        @Test
        public void validateDto_EndDateBeforeStartDate_ThrowsException() {
                CreateContractDTO dto = new CreateContractDTO();
                dto.setStartDate("2023-12-31");
                dto.setEndDate("2023-01-01");

                try {
                        contractService.validateDto(dto, LocalDate.parse(dto.getStartDate()),
                                        LocalDate.parse(dto.getEndDate()));
                } catch (IllegalArgumentException e) {
                        assertEquals("End date cannot be before start date", e.getMessage());
                        return; // Test passes if exception is thrown
                }
                // If we reach here, the test fails
                assert false : "Expected IllegalArgumentException was not thrown";
        }

        @Test
        public void fromEntity_MapsContractToResponseContractDTO() {
                Contract contract = new Contract();
                contract.setId(1);
                contract.setEmployee(new Employee());
                contract.getEmployee().setId(1);
                contract.setDepartment(Department.SALES);
                contract.setContractType(ContractType.FULL_TIME);
                contract.setSalaryAmount(new BigDecimal("50000.00"));
                contract.setHoursPerWeek(40);
                contract.setStartDate(LocalDate.parse("2023-01-01"));
                contract.setEndDate(LocalDate.parse("2023-12-31"));

                ResponseContractDTO dto = ResponseContractDTO.fromEntity(contract);

                assertNotNull(dto);
                assertEquals(contract.getId(), dto.getId());
                assertEquals(contract.getEmployee().getId(), dto.getEmployeeId());
                assertEquals(contract.getDepartment().name(), dto.getDepartment());
                assertEquals(contract.getContractType().name(), dto.getContractType());
                assertEquals(contract.getSalaryAmount(), dto.getSalaryAmount());
                assertEquals(contract.getHoursPerWeek(), dto.getHoursPerWeek());
                assertEquals("2023-01-01", dto.getStartDate());
                assertEquals("2023-12-31", dto.getEndDate());
        }

        @Test
        public void isActive_ReturnsTrueIfEndDateIsNullOrAfterToday() {
                Contract contract = new Contract();
                contract.setEndDate(null);
                assertEquals(true, contract.isActive());

                contract.setEndDate(LocalDate.now().plusDays(1));
                assertEquals(true, contract.isActive());

                contract.setEndDate(LocalDate.now().minusDays(1));
                assertEquals(false, contract.isActive());
        }

        @Test
        public void isActive_ReturnsFalseIfTodayIsAfterEndDate() {
                Contract contract = new Contract();
                contract.setEndDate(LocalDate.now().minusDays(2));
                assertEquals(false, contract.isActive());
        }

}
