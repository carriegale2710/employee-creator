import { describe, expect, it } from "vitest";
import { render, screen } from "@testing-library/react";
import userEvent from "@testing-library/user-event";
import "@testing-library/jest-dom"; // import jest-dom matchers
import EmployeeForm from "./EmployeeForm"; // adjust path as needed
import { employee1 } from "../../services/mockEmployees";

describe("EmployeeForm", () => {
  it("renders required input fields", () => {
    render(<EmployeeForm />);
    expect(screen.getByLabelText(/first name/i)).toBeInTheDocument();
    expect(screen.getByLabelText(/last name/i)).toBeInTheDocument();
    expect(screen.getByLabelText(/email/i)).toBeInTheDocument();
    expect(screen.getByLabelText(/phone/i)).toBeInTheDocument();
    expect(screen.getByLabelText(/address/i)).toBeInTheDocument();
  });

  it("allows user to fill out required fields and submit", async () => {
    render(<EmployeeForm />);
    const user = userEvent.setup();

    await user.type(screen.getByLabelText(/first name/i), employee1.firstName);
    await user.type(screen.getByLabelText(/last name/i), employee1.lastName);
    await user.type(screen.getByLabelText(/email/i), employee1.email);
    await user.type(screen.getByLabelText(/phone/i), employee1.phone);
    await user.type(screen.getByLabelText(/address/i), employee1.address ?? "");

    const submitButton = screen.getByRole("button", { name: /submit/i });
    expect(submitButton).toBeEnabled();

    await user.click(submitButton);
  });
  it("shows validation errors for empty required fields", async () => {
    render(<EmployeeForm />);
    const user = userEvent.setup();

    const submitButton = screen.getByRole("button", { name: /submit/i });

    await user.click(submitButton);

    expect(screen.getByText(/first name is required/i)).toBeInTheDocument();
    expect(screen.getByText(/last name is required/i)).toBeInTheDocument();
    expect(screen.getByText(/email is required/i)).toBeInTheDocument();
    expect(screen.getByText(/phone is required/i)).toBeInTheDocument();
  });
});
