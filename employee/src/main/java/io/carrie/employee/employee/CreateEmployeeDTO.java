package io.carrie.employee.employee;

import jakarta.validation.constraints.NotBlank;

public class CreateEmployeeDTO {

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    private String email;

}
