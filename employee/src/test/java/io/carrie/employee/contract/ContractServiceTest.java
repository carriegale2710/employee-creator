package io.carrie.employee.contract;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import io.carrie.employee.contract.dtos.CreateContractDTO;
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
}
