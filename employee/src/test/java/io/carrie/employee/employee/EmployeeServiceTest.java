package io.carrie.employee.employee;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.modelmapper.ModelMapper;

public class EmployeeServiceTest {

    // provide fake implementation for control data comparison (testing logic not
    // API/repo)

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private ModelMapper modelMapper;

    @Spy // call existing or mock
    @InjectMocks
    private EmployeeService employeeService;

    // setup your mock data for testing
    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test // testing findAll()
    public void findAll_CallsFindAllOnRepo() {
        this.employeeService.findAll();
        // no additional logic, just test it was called at all
        verify(this.employeeRepository).findAll();
    }

    @Test
    public void findById_CallsFindByIdOnRepo() {
        this.employeeService.findById(1);
        verify(this.employeeRepository).findById(1);
    }

    @Test
    public void deleteById_CallsDeleteByIdOnRepo() {
        doReturn(Optional.of(new Employee())).when(employeeService).findById(1);
        boolean result = this.employeeService.deleteById(1);
        verify(this.employeeRepository).deleteById(1);
        assertEquals(true, result);
    }

    @Test // this will have some logic to check data that gets returned is correct
    public void create_CallsSaveOnRepo() {

        // control data to compare what returned data should look like
        Employee savedEmployee = new Employee();
        savedEmployee.setFirstName("Timmy");
        savedEmployee.setLastName("Turner");
        savedEmployee.setEmail("timmehhh@example.com");

        // mock data to test if saves properly
        CreateEmployeeDTO data = new CreateEmployeeDTO("Timmy", "Turner", "timmehhh@example.com");

        // NOTE - the data is not based on dependencies, we just tell them what to
        // return manually (savedEmployee)
        // why? we are testing that the service is working, not modelMapper/repo
        // service!!

        // mock what modelMapper returns on map (not actually called, just use it as a
        // trigger, this won't work in e2e tests)
        when(this.modelMapper.map(data, Employee.class)).thenReturn(savedEmployee);
        // mock what repo should return on save
        when(this.employeeRepository.save(any(Employee.class))).thenReturn(savedEmployee);

        // check that save method called by repo
        Employee result = this.employeeService.create(data);
        verify(this.employeeRepository).save(savedEmployee);
        // check that returned data is not null
        assertNotNull(result);
        // check that returned data is correct
        assertEquals(savedEmployee, result);

    }

}
