import { describe, expect, it } from "vitest";
import { render, screen } from "@testing-library/react";
import userEvent from "@testing-library/user-event";
import "@testing-library/jest-dom"; // import jest-dom matchers
import {
  contract1,
  invalidContract,
  missingFieldsContract,
} from "../../services/mockContracts";
import ContractForm from "./ContractForm"; // adjust path as needed
import { MemoryRouter } from "react-router-dom";

describe("ContractForm", () => {
  it("renders all input fields", () => {
    render(
      <MemoryRouter>
        <ContractForm />
      </MemoryRouter>
    );

    expect(screen.getByLabelText(/employee id/i)).toBeInTheDocument();
    expect(screen.getByLabelText(/department/i)).toBeInTheDocument();
    expect(screen.getByLabelText(/contract type/i)).toBeInTheDocument();
    expect(screen.getByLabelText(/salary amount/i)).toBeInTheDocument();
    expect(screen.getByLabelText(/hours/i)).toBeInTheDocument();
    expect(screen.getByLabelText(/start date/i)).toBeInTheDocument();
    expect(screen.getByLabelText(/end date/i)).toBeInTheDocument(); // Optional field
  });

  it("allows user to fill out required fields and submit with valid data", async () => {
    render(
      <MemoryRouter>
        <ContractForm />
      </MemoryRouter>
    );
    const user = userEvent.setup();

    await user.type(
      screen.getByLabelText(/employee id/i),
      String(contract1.id)
    );
    await user.type(
      screen.getByLabelText(/department/i),
      String(contract1.department)
    );
    await user.type(
      screen.getByLabelText(/contract type/i),
      String(contract1.contractType)
    );
    await user.type(
      screen.getByLabelText(/salary amount/i),
      String(contract1.salaryAmount)
    );
    await user.type(
      screen.getByLabelText(/hours/i),
      String(contract1.hoursPerWeek)
    );
    await user.type(
      screen.getByLabelText(/start date/i),
      String(contract1.startDate)
    );
    await user.type(
      screen.getByLabelText(/end date/i),
      String(contract1.endDate)
    );
    const submitButton = screen.getByRole("button", { name: /submit/i });
    expect(submitButton).toBeEnabled();

    await user.click(submitButton);
  });

  it("shows validation errors for submitting with empty required fields", async () => {
    render(
      <MemoryRouter>
        <ContractForm />
      </MemoryRouter>
    );
    const user = userEvent.setup();

    // Don't type anything - leave fields empty to trigger validation
    const submitButton = screen.getByRole("button", { name: /submit/i });

    // Try to submit with empty fields
    await user.click(submitButton);

    // Check for validation error messages (only for required fields)
    expect(screen.getByText(/employee id is required/i)).toBeInTheDocument();
    expect(screen.getByText(/department is required/i)).toBeInTheDocument();
    expect(screen.getByText(/contract type is required/i)).toBeInTheDocument();
    expect(screen.getByText(/salary amount is required/i)).toBeInTheDocument();
    expect(screen.getByText(/hours per week is required/i)).toBeInTheDocument();
    expect(screen.getByText(/start date is required/i)).toBeInTheDocument();
    // Note: endDate is optional (required: false), so no validation error expected
  });
});
