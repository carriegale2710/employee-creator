package io.carrie.employee.employee;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.*;

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

    // SECTION - GET "/employees"

    @Test
    public void getAllEmployees_EmployeesInDB_ReturnsSuccess() {
        given()
                .when().get("/employees")
                .then().statusCode(HttpStatus.OK.value())
                .body("$", hasSize(2)) // should return 2 records in list
                .body(matchesJsonSchemaInClasspath("schemas/employee-list-schema.json"));
    }

    @Test
    public void getAllEmployees_NoEmployeesInDB_ReturnsSuccessAndEmptyArray() {
        this.employeeRepository.deleteAll();
        given()
                .when().get("/employees")
                .then().statusCode(HttpStatus.OK.value())
                .body("$", hasSize(0));
    }

    // SECTION - GET "/employees/{id}"

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

    // TODO - (edge) employee id found but data is private/needs login authorisation

    @Test
    public void getEmployeeById_EmployeeNotInDB_NotFound() {
        given()
                .when().get("/employees/999")
                .then().statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    public void getEmployeeById_IdNotValid_BadRequest() {
        given()
                .when().get("/employees/sfee23")
                .then().statusCode(HttpStatus.BAD_REQUEST.value());
    }

    // SECTION - DELETE "/employees/{id}"

    @Test
    public void deleteEmployeeById_EmployeeInDb_ReturnsSuccessNoContent() {
        Integer existingId = employeeList.get(0).getId();
        given()
                .when().delete("employees/" + existingId)
                .then().statusCode(HttpStatus.NO_CONTENT.value())
                .body("$", hasSize(0));
        // todo - id must be double-checked with data
        // todo - check record was actually deleted from repo
    }

    @Test
    public void deleteEmployeeById_EmployeeNotInDB_NotFound() {
        given()
                .when().delete("/employees/999")
                .then().statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    public void deleteEmployeeById_IdNotValid_BadRequest() {
        given()
                .when().delete("/employees/sfee23")
                .then().statusCode(HttpStatus.BAD_REQUEST.value());
    }

    // SECTION - POST "/employees"

    @Test
    public void createEmployee_ValidData_Created() {

        HashMap<String, String> data = new HashMap<>();
        data.put("firstName", "Cosmo");
        data.put("lastName", "Cosma");
        data.put("email", "cosmo@example.com");

        given()
                .contentType(ContentType.JSON).body(data)
                .when().post("/employees")
                .then().statusCode(HttpStatus.CREATED.value())
                .body("firstName", equalTo("Cosmo")); // returns correct data
        // todo - check data was successfully added as new employee in DB

    }

    @Test
    public void createEmployee_MissingData_BadRequest() {
        HashMap<String, String> data = new HashMap<>();
        data.put("firstName", "Cosmo");

        given()
                .contentType(ContentType.JSON).body(data)
                .when().post("/employees")
                .then().statusCode(HttpStatus.BAD_REQUEST.value());

    }

    @Test
    public void createEmployee_InvalidName_BadRequest() {

        HashMap<String, String> data = new HashMap<>();

        data.put("firstName", "Cosmo");
        data.put("lastName", "Cosma3q43"); // invalid name
        data.put("email", "cosmo@example.com");

        given()
                .contentType(ContentType.JSON).body(data)
                .when().post("/employees")
                .then().statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void createEmployee_InvalidEmail_BadRequest() {

        HashMap<String, String> data = new HashMap<>();

        data.put("firstName", "Cosmo");
        data.put("lastName", "Cosma");
        data.put("email", "cosmoexample.com"); // invalid email

        given()
                .contentType(ContentType.JSON).body(data)
                .when().post("/employees")
                .then().statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void createEmployee_DuplicateData_BadRequest() {

        HashMap<String, String> data = new HashMap<>();
        data.put("firstName", "Timmy");
        data.put("lastName", "Turner");
        data.put("email", "timmehhh@example.com");

        given()
                .contentType(ContentType.JSON).body(data)
                .when().post("/employees")
                .then().statusCode(HttpStatus.BAD_REQUEST.value());
        // todo NOTE - this should not be 500 internal server error but 400!
    }

    @Test
    public void createEmployee_DuplicateEmail_BadRequest() {

        HashMap<String, String> data = new HashMap<>();
        data.put("firstName", "Icky");
        data.put("lastName", "Vicky");
        data.put("email", "timmehhh@example.com"); // only email matters

        given()
                .contentType(ContentType.JSON).body(data)
                .when().post("/employees")
                .then().statusCode(HttpStatus.BAD_REQUEST.value());
    }

    // SECTION - PUT "/employees/{id}"
    // (on hold - for future editing features)
}
