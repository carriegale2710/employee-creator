package io.carrie.employee.employee;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.modelmapper.ModelMapper;

import io.carrie.employee.employee.dtos.CreateEmployeeDTO;
import io.carrie.employee.employee.dtos.UpdateEmployeeDTO;

public class EmployeeServiceTest {

        // NOTE (just testing the service works properly)
        // providing fake implementation for control data comparison
        // the data is not based on dependencies
        // won't work in e2e tests

        @Mock
        private EmployeeRepository employeeRepository;

        @Mock
        private ModelMapper modelMapper;

        @Spy // call existing or mock
        @InjectMocks
        private EmployeeService employeeService;

        @BeforeEach // setup your mock data for testing
        public void setup() {
                MockitoAnnotations.openMocks(this);
        }

        @Test
        public void findAll_CallsFindAllOnRepo() {
                this.employeeService.findAll();
                verify(this.employeeRepository).findAll();
        }

        @Test
        public void findById_CallsFindByIdOnRepo() {
                Employee employee = new Employee();

                when(this.employeeRepository.findById(1))
                                .thenReturn(java.util.Optional.of(employee));

                this.employeeService.findById(1);
                verify(this.employeeRepository).findById(1);
        }

        @Test
        public void deleteById_CallsDeleteByIdOnRepo() {
                doReturn(new Employee()).when(employeeService).findById(1);
                this.employeeService.deleteById(1);
                verify(this.employeeRepository).deleteById(1);
        }

        @Test
        public void create_CallsSaveOnRepo() {
                // Arrange- mock what should return (control vs mock data)
                Employee employee = new Employee();
                CreateEmployeeDTO dto = new CreateEmployeeDTO();

                when(this.modelMapper.map(dto, Employee.class))
                                .thenReturn(employee);

                when(this.employeeRepository.save(any(Employee.class)))
                                .thenReturn(employee);

                // Act -run the method
                Employee result = this.employeeService.create(dto);

                // Assert - check save method called by repo
                verify(this.employeeRepository).save(employee);

                // Assert -check data
                assertNotNull(result);
                assertEquals(employee, result);
        }

        @Test
        public void updateById_CallsSaveOnRepo() {
                Employee employee = new Employee();
                employee.setFirstName("Timmy");
                UpdateEmployeeDTO dto = new UpdateEmployeeDTO();
                dto.setFirstName("Timmy");

                when(this.employeeRepository.findById(1))
                                .thenReturn(java.util.Optional.of(employee));

                when(this.employeeRepository.save(any(Employee.class)))
                                .thenReturn(employee);

                // Act -run the method
                Employee result = this.employeeService.updateById(1, dto);

                // Assert - method call
                verify(this.employeeRepository).save(employee);
                // Assert -check data
                assertNotNull(result);
                assertEquals(employee, result);
        }
}
