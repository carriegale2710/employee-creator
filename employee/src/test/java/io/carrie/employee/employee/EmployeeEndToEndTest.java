package io.carrie.employee.employee;

import io.carrie.employee.employee.dtos.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class EmployeeEndToEndTest {

    @LocalServerPort
    private int port;

    @Autowired
    private EmployeeRepository employeeRepository;
    private ArrayList<Employee> employeeList = new ArrayList<>();

    @BeforeEach // set up data and save in db
    public void setUpEmployees() {
        RestAssured.port = this.port;

        // clear the data in mock DB before each test
        this.employeeRepository.deleteAll();
        this.employeeList.clear();

        // create some employees for testing:
        Employee employee1 = new Employee("Timmy", "Turner", "timmehhh@example.com", "0400000000", "123 Fairy Lane");
        this.employeeRepository.save(employee1);
        this.employeeList.add(employee1);

        Employee employee2 = new Employee("Wanda", "Cosmo", "wanda.cosmo@example.com", "0123456789", "123 Fairy Lane");
        this.employeeRepository.save(employee2);
        this.employeeList.add(employee2);

    }

    // test framework (AAA)
    // arrange - set up mock data and variables
    // act - run the code you are testing
    // assert - check result is correct

    @Nested
    @DisplayName("GET /employees")
    class GetEmployeesTests {

        @Test
        public void getAllEmployees_EmployeesInDB_ReturnsSuccess() {
            given()
                    .when().get("/employees")
                    .then().statusCode(HttpStatus.OK.value())
                    .body("$", hasSize(2)) // should return 2 records in list
                    .body(matchesJsonSchemaInClasspath("schemas/employees/employee-list-schema.json"));
        }

        @Test
        public void getAllEmployees_NoEmployeesInDB_ReturnsSuccessAndEmptyArray() {
            employeeRepository.deleteAll();
            given()
                    .when().get("/employees")
                    .then().statusCode(HttpStatus.OK.value())
                    .body("$", hasSize(0));
        }

    }

    @Nested
    @DisplayName("GET /employees/{id}")
    class GetEmployeeByIdTests {

        @Test
        public void getEmployeeById_EmployeeInDB_ReturnsSuccess() {
            Integer existingId = employeeList.get(0).getId();
            given()
                    .when().get("/employees/" + existingId)
                    .then().statusCode(HttpStatus.OK.value());
        }

        @Test
        public void getEmployeeById_EmployeeInDB_ReturnsCorrectData() {
            Integer existingId = employeeList.get(1).getId();
            given()
                    .when().get("/employees/" + existingId)
                    .then().statusCode(HttpStatus.OK.value())
                    .body("id", equalTo(existingId))
                    .body("firstName", equalTo("Wanda"))
                    .body("lastName", equalTo("Cosmo"))
                    .body("email", equalTo("wanda.cosmo@example.com"))
                    .body(matchesJsonSchemaInClasspath("schemas/employees/employee-schema.json"));
        }

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

    }

    @Nested
    @DisplayName("DELETE /employees/{id}")
    class DeleteEmployeeByIdTests {

        @Test
        public void deleteEmployeeById_EmployeeInDb_SuccessNoContent() {
            Integer existingId = employeeList.get(0).getId();
            given()
                    .when().delete("/employees/" + existingId)
                    .then().statusCode(HttpStatus.NO_CONTENT.value());
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

    }

    private String assertBadRequest(Method httpMethod, String endpoint, Object data) {
        Response response = given()
                .contentType(ContentType.JSON)
                .body(data)
                .when()
                .request(httpMethod, endpoint);
        response.then().statusCode(HttpStatus.BAD_REQUEST.value());
        String error = response.jsonPath().getString("errors.defaultMessage"); // error message
        System.out.println(error);
        return error;
    }

    @Nested
    @DisplayName("POST /employees")
    class CreateEmployeeByIdTests {

        private CreateEmployeeDTO employeeDto = new CreateEmployeeDTO(
                "Cosmo",
                "Cosma",
                "cosmo@example.com",
                "9876543210",
                "123 Fairy Lane");

        private String assertPost(CreateEmployeeDTO employeeDto) {
            return assertBadRequest(Method.POST, "/employees", employeeDto);
        }

        @Test
        public void create_ValidData_Created() {
            Response response = given()
                    .contentType(ContentType.JSON)
                    .body(employeeDto)
                    .when()
                    .post("/employees");

            response.then()
                    .statusCode(HttpStatus.CREATED.value())
                    .body("firstName", equalTo("Cosmo")); // returns correct data?
        }

        @Test
        public void create_MissingData_BadRequest() {
            employeeDto.setFirstName(null);
            assertPost(employeeDto);
        }

        @Test
        public void create_InvalidFirstName_BadRequest() {
            employeeDto.setFirstName("Cosmo.,.");
            assertPost(employeeDto);
            employeeDto.setFirstName("Cosmo2435");
            assertPost(employeeDto);
            employeeDto.setFirstName("thisisareallylongnamethatisExceedingTheCharLimit");
            assertPost(employeeDto);
        }

        @Test
        public void create_InvalidLastName_BadRequest() {
            employeeDto.setLastName("Cosmo.,.");
            assertPost(employeeDto);
            employeeDto.setLastName("Cosmo2435");
            assertPost(employeeDto);
            employeeDto.setLastName("thisisareallylongnamethatisExceedingTheCharLimit");
            assertPost(employeeDto);
        }

        @Test
        public void create_InvalidEmail_BadRequest() {
            employeeDto.setEmail("cosmoexample.com"); // not email format
            assertPost(employeeDto);
        }

        @Test
        public void create_EmailIsTaken_BadRequest() {
            employeeDto.setEmail("timmehhh@example.com");
            String expectedError = "Employee with email 'timmehhh@example.com' already exists";

            String actualError = given()
                    .contentType(ContentType.JSON)
                    .body(employeeDto)
                    .when()
                    .post("/employees")
                    .then()
                    .statusCode(400)
                    .extract()
                    .asString(); // plain string response

            assertEquals(expectedError.trim(), actualError.trim());

        }

    }
    // SECTION - PATCH "/employees/{id}"
    // (on hold - for future editing features)

    @Nested
    @DisplayName("PATCH /employees/{id}")
    class UpdateEmployeeByIdTests {

        private void assertPatch(UpdateEmployeeDTO employeeDto) {
            assertBadRequest(Method.PATCH, "/employees/1", employeeDto); // timmy
        }

        UpdateEmployeeDTO employeeDto = new UpdateEmployeeDTO();

        @Test
        public void patchById_ValidData_Created() {
            // arrange
            employeeDto.setEmail("timtim@example.com");
            // ensure employee with id 1 exists
            Integer id = employeeList.get(0).getId();
            // act
            given().contentType(ContentType.JSON).body(employeeDto)
                    .when().patch("/employees/" + id)
                    // assert - updates correct data?
                    .then().statusCode(HttpStatus.OK.value())
                    .body("email", equalTo("timtim@example.com"));
        }

        @Test
        public void patchById_InvalidFirstName_BadRequest() {
            employeeDto.setFirstName("Cosmo,,.");
            assertPatch(employeeDto);
            employeeDto.setFirstName("hasNumbers3245");
            assertPatch(employeeDto);
            employeeDto.setFirstName(
                    "thisisareallyreallyreallylongnamethatistoolong");
            assertPatch(employeeDto);
        }

        @Test
        public void patchById_InvalidLastName_BadRequest() {
            employeeDto.setLastName("Cosmo,,.");
            assertPatch(employeeDto);
            employeeDto.setLastName("hasNumbers3245");
            assertPatch(employeeDto);
            employeeDto.setLastName(
                    "thisisareallyreallyreallylongnamethatistoolong");
            assertPatch(employeeDto);
        }

        @Test
        public void patchById_InvalidEmail_BadRequest() {
            employeeDto.setEmail("cosmoexample.com"); // invalid email
            assertPatch(employeeDto);
        }

        @Test
        public void patchById_EmailIsTaken_BadRequest() {
            String takenEmail = employeeList.get(0).getEmail();
            Integer differentEmployeeId = employeeList.get(1).getId(); // Use a different employee
            employeeDto.setEmail(takenEmail);

            String expectedError = "Employee with email already exists";
            String actualError = given().contentType(ContentType.JSON).body(employeeDto)
                    .when().patch("/employees/" + differentEmployeeId)
                    .then().statusCode(400)
                    .extract().asString(); // plain string response

            assertEquals(expectedError.trim(), actualError.trim()); // todo- fix

        }

    }
}
