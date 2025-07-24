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

    @Pattern(regexp = "^\\+?\\d{7,15}$", message = "Invalid phone number.")
    private String phone;

    private String address;

    public UpdateEmployeeDTO() {

    }

    public UpdateEmployeeDTO(String firstName, String lastName, String email, String phone, String address) {
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
