package io.carrie.employee.employee.e2e;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

import io.carrie.employee.employee.Employee;
import io.carrie.employee.employee.EmployeeRepository;
import io.restassured.RestAssured;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class EmployeeEndToEndTest {
    @LocalServerPort
    private int port;

    @Autowired
    private EmployeeRepository employeeRepository;
    private ArrayList<Employee> employeeList = new ArrayList<>();

    // set up data and save in db
    @BeforeEach
    public void setUp() {
        RestAssured.port = this.port;

        // clear the data in mock DB before each test
        this.employeeRepository.deleteAll();
        this.employeeList.clear();

        // create some employees for testing:

        /*
         * {
         * "id": 1,
         * "first_name": "Timmy",
         * "last_name": "Turner",
         * "email": "timmehhh@example.com",
         * },
         * {
         * "id": 2,
         * "first_name": "Cosmo",
         * "last_name": "Wanda",
         * "email": "cosmo@example.com",
         * }
         */

        Employee employee1 = new Employee();
        employee1.setFirstName("Timmy");
        employee1.setLastName("Turner");
        employee1.setEmail("timmehhh@example.com");
        this.employeeRepository.save(employee1);
        this.employeeList.add(employee1);

        Employee employee2 = new Employee();
        employee2.setFirstName("Wanda");
        employee2.setLastName("Cosmo");
        employee2.setEmail("wanda.cosmo@example.com");
        this.employeeRepository.save(employee2);
        this.employeeList.add(employee2);

    }

    // test for /employees
    // arrange
    // act
    // assert

    @Test
    public void getAllEmployees_EmployeesInDB_ReturnsSuccess() {
        given()
                .when().get("/employees")
                .then().statusCode(HttpStatus.OK.value())
                .body("$", hasSize(2)); // should return 2 records in list
    }

    @Test
    public void getAllEmployees_NoEmployeesInDB_ReturnsSuccessAndEmptyArray() {
        this.employeeRepository.deleteAll();
        given()
                .when().get("/books")
                .then().statusCode(HttpStatus.OK.value())
                .body("$", hasSize(0));
    }

}
