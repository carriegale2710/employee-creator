package io.carrie.employee.employee;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class CreateEmployeeDTO {

    @NotBlank
    @NotNull
    @Pattern(regexp = "^[a-zA-Z]{2,30}$", message = "First name must be 2 to 30 characters long and contain only letters.")
    private String firstName;

    @NotBlank
    @NotNull
    @Pattern(regexp = "^[a-zA-Z]{2,30}$", message = "Last name must be 2 to 30 characters long and contain only letters.")
    private String lastName;

    @Email
    @NotNull
    private String email;

    public CreateEmployeeDTO() {

    }

    public CreateEmployeeDTO(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
