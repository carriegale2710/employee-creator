package io.carrie.employee.employee;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;

import io.carrie.employee.employee.Employee;
import io.carrie.employee.employee.EmployeeRepository;
import io.restassured.RestAssured;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class EmployeeEndToEndTest {

    @LocalServerPort
    private int port;

    @Autowired
    private EmployeeRepository employeeRepository;
    private ArrayList<Employee> employeeList = new ArrayList<>();

    @BeforeEach // set up data and save in db
    public void setUp() {
        RestAssured.port = this.port;

        // clear the data in mock DB before each test
        this.employeeRepository.deleteAll();
        this.employeeList.clear();

        System.out.println(employeeList);

        // create some employees for testing:

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

    @Test
    public void printAllEmployees() {
        employeeRepository.findAll().forEach(System.out::println);
    }

    // test for /employees
    // arrange
    // act
    // assert

    // SECTION - getAll()
    // should return correct all records
    // should not throw error if no records yet

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
                .when().get("/employees")
                .then().statusCode(HttpStatus.OK.value())
                .body("$", hasSize(0));
    }

    // SECTION - getById()

    // case 1: employee id found
    //// returns 200 OK
    //// returns data with correct id
    // (edge) if data is private info, needs authorisation for access

    @Test
    public void getEmployeeById_EmployeeInDB_ReturnsSuccess() {
        Integer existingId = this.employeeList.get(0).getId();
        given()
                .when().get("/employees/" + existingId)
                .then().statusCode(HttpStatus.OK.value());
    }

    @Test
    public void getEmployeeById_EmployeeInDB_ReturnsCorrectData() {
        Integer existingId = this.employeeList.get(1).getId();
        given()
                .when().get("/employees/" + existingId)
                .then().statusCode(HttpStatus.OK.value())
                .body("id", equalTo(existingId))
                .body("firstName", equalTo("Wanda"))
                .body("lastName", equalTo("Cosmo"))
                .body("email", equalTo("wanda.cosmo@example.com"))
                .body(matchesJsonSchemaInClasspath("schemas/employee-schema.json"));
    }

    // case 2: employee id NOT found
    // returns 404 not found error

    @Test
    public void getEmployeeById_EmployeeNotInDB_NotFound() {
        given()
                .when().get("/employees/999")
                .then().statusCode(HttpStatus.NOT_FOUND.value());
    }

    // case 3: id is not valid
    // returns bad request error

    @Test
    public void getEmployeeById_IdNotValid_BadRequest() {
        given()
                .when().get("/employees/sfee23")
                .then().statusCode(HttpStatus.BAD_REQUEST.value());
    }

    // SECTION - deleteById

    // case 1 - id found in DB
    // data is valid and correct
    // data is invalid or corrupt

    // case 2 - id not found in DB

    // SECTION - create

    // case 1 - successfully added new employee to DB
    // returns 201 CREATED
    // returns new employee data saved in DB

    // 2.1 - DTO data is invalid or missing (BAD REQUEST)
    // 2.2 - duplicate data already exists in DB
    // 2.3 - email or phone number is already used
    // 2.4 - department does not exist in DB

}
