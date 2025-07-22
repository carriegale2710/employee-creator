// Card.test.tsx
import { render, screen } from "@testing-library/react";
import { describe, expect, it } from "vitest";
import Card from "./Card";
import { employee1 } from "../../services/mockEmployees";

describe("Card", () => {
  it("has Edit and Delete buttons", () => {
    render(<Card employee={employee1} />);

    expect(screen.getByRole("button", { name: /edit/i })).toBeInTheDocument();
    expect(screen.getByRole("button", { name: /delete/i })).toBeInTheDocument();
  });

  it("Renders a Card with text content", () => {
    render(<Card employee={employee1} />);
    expect(screen.getAllByTestId("card")).toBeInTheDocument();
    expect(screen.getAllByTestId("card")).not.toBeEmptyDOMElement();
    expect(screen.getAllByTestId("card")).toHaveTextContent(/\S/);
  });

  it("shows all the correct data", () => {
    render(<Card employee={employee1} />);
    expect(screen.findByText(employee1.firstName)).toBeInTheDocument();
    expect(screen.findByText(employee1.lastName)).toBeInTheDocument();
    expect(screen.findByText(employee1.email)).toBeInTheDocument();
    expect(screen.findByText(employee1.phone)).toBeInTheDocument();
    expect(screen.findByText(employee1.address)).toBeInTheDocument();
  });
});
