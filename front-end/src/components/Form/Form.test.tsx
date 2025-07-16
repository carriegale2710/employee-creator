// Test goal: Starter test for CreateEmployeeForm with CreateEmployeeDTO

import { render, screen } from "@testing-library/react";
import userEvent from "@testing-library/user-event";
import Form from "./Form"; // adjust path as needed
import { describe, expect, it } from "vitest";
import { employee1 } from "../../services/mockEmployees";

describe("CreateEmployeeForm", () => {
  it("renders required input fields", () => {
    render(<Form />);
    expect(screen.getByLabelText(/first name/i)).toBeInTheDocument();
    expect(screen.getByLabelText(/last name/i)).toBeInTheDocument();
    expect(screen.getByLabelText(/phone number/i)).toBeInTheDocument();
    expect(screen.getByLabelText(/email/i)).toBeInTheDocument();
    expect(screen.getByLabelText(/address/i)).toBeInTheDocument();
    expect(screen.getByLabelText(/date of birth/i)).toBeInTheDocument();
    expect(screen.getByLabelText(/tax file number/i)).toBeInTheDocument();
  });

  it("allows user to fill out required fields and submit", async () => {
    render(<Form />);
    const user = userEvent.setup();

    await user.type(screen.getByLabelText(/first name/i), employee1.firstName);
    await user.type(screen.getByLabelText(/last name/i), employee1.lastName);
    await user.type(screen.getByLabelText(/email/i), employee1.email);
    await user.type(
      screen.getByLabelText(/phone number/i),
      employee1.phoneNumber
    );
    await user.type(screen.getByLabelText(/address/i), employee1.address);
    await user.type(
      screen.getByLabelText(/date of birth/i),
      employee1.dateOfBirth
    );
    await user.type(
      screen.getByLabelText(/tax file number/i),
      employee1.taxFileNumber
    );

    const submitButton = screen.getByRole("button", { name: /submit/i });
    expect(submitButton).toBeEnabled();

    await user.click(submitButton);
  });
});
