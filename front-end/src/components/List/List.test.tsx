import { render, screen } from "@testing-library/react";
import { describe, expect, it } from "vitest";
import List from "./List";
import type { Employee } from "../../services/employees";
import { mockEmployee } from "../Card/Card.test";

const mockEmployees: Employee[] = [mockEmployee, mockEmployee];

describe("List", () => {
  it("checks true", () => {
    expect(true).toBe(true);
  });

  it("Renders a Card with text content", () => {
    render(<List employees={mockEmployees} />);
    expect(screen.getAllByTestId("card")).toBeInTheDocument();
    expect(screen.getAllByTestId("card")).not.toBeEmptyDOMElement();
    expect(screen.getAllByTestId("card")).toHaveTextContent(/\S/);
  });
});
