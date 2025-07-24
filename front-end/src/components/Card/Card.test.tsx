// Card.test.tsx
import { render, screen } from "@testing-library/react";
import { describe, expect, it } from "vitest";
import { MemoryRouter } from "react-router-dom";
import Card from "./Card";
import { employee1 } from "../../services/mockEmployees";

describe("Card", () => {
  it("has Edit and Delete buttons", () => {
    render(
      <MemoryRouter>
        <Card employee={employee1} />
      </MemoryRouter>
    );
    expect(screen.getByRole("button", { name: /Edit/i })).toBeInTheDocument();
    expect(screen.getByRole("button", { name: /Delete/i })).toBeInTheDocument();
  });

  it("renders text content", () => {
    render(
      <MemoryRouter>
        <Card employee={employee1} />
      </MemoryRouter>
    );
    expect(screen.getByTestId("card")).toBeInTheDocument();
    expect(screen.getByTestId("card")).not.toBeEmptyDOMElement();
    expect(screen.getByTestId("card")).toHaveTextContent(/\S/);
  });

  it("shows all the correct data", () => {
    render(
      <MemoryRouter>
        <Card employee={employee1} />
      </MemoryRouter>
    );
    // Check for full name in a single text element
    expect(
      screen.getByText(`${employee1.firstName} ${employee1.lastName}`)
    ).toBeInTheDocument();
    expect(screen.getByText(employee1.email)).toBeInTheDocument();
    // Note: phone and address are not rendered in this component
  });
});
