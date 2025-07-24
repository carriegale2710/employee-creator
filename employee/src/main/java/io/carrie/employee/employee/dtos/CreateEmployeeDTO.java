package io.carrie.employee.employee.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class CreateEmployeeDTO {

    @NotBlank
    @Pattern(regexp = "^[a-zA-Z]{2,30}$", message = "Invalid First name.")
    private String firstName;

    @NotBlank
    @Pattern(regexp = "^[a-zA-Z]{2,30}$", message = "Invalid Last name.")
    private String lastName;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Pattern(regexp = "^\\+?\\d{7,15}$", message = "Invalid phone number.")
    private String phone;

    private String address;

    public CreateEmployeeDTO() {

    }

    public CreateEmployeeDTO(String firstName, String lastName, String email, String phone, String address) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.address = address;
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

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setAddress(String address) {
        this.address = address;
    }

}
