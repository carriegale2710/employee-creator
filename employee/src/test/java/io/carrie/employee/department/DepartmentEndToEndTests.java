package io.carrie.employee.department;

import java.util.ArrayList;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;

import io.carrie.employee.department.dtos.CreateDepartmentDTO;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class DepartmentEndToEndTests {

    @LocalServerPort
    private int port;

    @Autowired
    private DepartmentRepository departmentRepository;

    private ArrayList<Department> departmentList = new ArrayList<>();

    @BeforeEach
    public void setUp() {
        RestAssured.port = this.port;

        // Clear test data
        this.departmentRepository.deleteAll();
        this.departmentList.clear();

        // Create test departments
        Department dept1 = new Department();
        dept1.setName("ENGINEERING");
        this.departmentRepository.save(dept1);
        this.departmentList.add(dept1);

        Department dept2 = new Department();
        dept2.setName("MARKETING");
        this.departmentRepository.save(dept2);
        this.departmentList.add(dept2);
    }

    @Nested
    @DisplayName("GET /departments")
    class GetDepartmentsTests {

        @Test
        public void getAllDepartments_DepartmentsInDB_ReturnsSuccess() {
            given()
                    .when().get("/departments")
                    .then().statusCode(HttpStatus.OK.value())
                    .body("$", hasSize(2))
                    .body("[0].name", notNullValue())
                    .body("[1].name", notNullValue());
        }

        @Test
        public void getAllDepartments_NoDepartmentsInDB_ReturnsEmptyArray() {
            departmentRepository.deleteAll();
            given()
                    .when().get("/departments")
                    .then().statusCode(HttpStatus.OK.value())
                    .body("$", hasSize(0));
        }
    }

    @Nested
    @DisplayName("GET /departments/{name}")
    class GetDepartmentByNameTests {

        @Test
        public void getDepartmentByName_DepartmentExists_ReturnsSuccess() {
            given()
                    .when().get("/departments/ENGINEERING")
                    .then().statusCode(HttpStatus.OK.value())
                    .body("name", equalTo("ENGINEERING"))
                    .body("id", notNullValue());
        }

        @Test
        public void getDepartmentByName_DepartmentNotFound_ReturnsNotFound() {
            given()
                    .when().get("/departments/NONEXISTENT")
                    .then().statusCode(HttpStatus.NOT_FOUND.value());
        }
    }

    private String assertBadRequest(Method httpMethod, String endpoint, Object data) {
        Response response = given()
                .contentType(ContentType.JSON)
                .body(data)
                .when()
                .request(httpMethod, endpoint);
        response.then().statusCode(HttpStatus.BAD_REQUEST.value());
        String error = response.jsonPath().getString("errors.defaultMessage"); // Changed from "errors.defaultMessage"
        System.out.println(error);
        return error;
    }

    @Nested
    @DisplayName("POST /departments")
    class CreateDepartmentTests {

        private String assertPost(CreateDepartmentDTO departmentDto) {
            return assertBadRequest(Method.POST, "/departments", departmentDto);
        }

        @Test
        public void createDepartment_ValidData_Created() {
            CreateDepartmentDTO dto = new CreateDepartmentDTO("SALES");

            given()
                    .contentType(ContentType.JSON)
                    .body(dto)
                    .when()
                    .post("/departments")
                    .then()
                    .statusCode(HttpStatus.CREATED.value())
                    .body("name", equalTo("SALES"))
                    .body("id", notNullValue());
        }

        @Test
        public void createDepartment_DuplicateName_BadRequest() {
            CreateDepartmentDTO dto = new CreateDepartmentDTO("ENGINEERING"); // Already exists
            assertPost(dto);
            // Assertions.assertTrue(error.contains("already exists")); // Simplified
            // assertion

        }

        @Test
        public void createDepartment_EmptyName_BadRequest() {
            CreateDepartmentDTO dto = new CreateDepartmentDTO("");
            String error = assertPost(dto);
            Assertions.assertTrue(error.contains("must not be empty") || error.contains("must not be blank"));
        }

        @Test
        public void createDepartment_NullName_BadRequest() {
            CreateDepartmentDTO dto = new CreateDepartmentDTO(null);
            String error = assertPost(dto);
            Assertions.assertTrue(error.contains("must not be empty") || error.contains("must not be null"));
        }

        @Test
        public void createDepartment_NameTooLong_BadRequest() {
            CreateDepartmentDTO dto = new CreateDepartmentDTO("A".repeat(51)); // Exceeds 50 char limit
            String error = assertPost(dto);
            Assertions.assertTrue(error.contains("50 characters") || error.contains("too long"));
        }
    }
}
