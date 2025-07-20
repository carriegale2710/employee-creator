package io.carrie.employee.employee.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;

public class UpdateEmployeeDTO {

    // NOTE - all these fields are optional

    @Pattern(regexp = "^[a-zA-Z]{2,30}$", message = "Enter valid first name.")
    private String firstName;

    @Pattern(regexp = "^[a-zA-Z]{2,30}$", message = "Enter valid last name.")
    private String lastName;

    @Email
    private String email;

    public UpdateEmployeeDTO() {

    }

    public UpdateEmployeeDTO(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

}
