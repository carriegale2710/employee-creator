import { describe, expect, it } from "vitest";
import { render, screen } from "@testing-library/react";
import Button from "./Button";

describe("Button", () => {
  it("checks true", () => {
    expect(true).toBe(true);
  });

  it("Renders a button", () => {
    render(<Button>Click me!</Button>);
    expect(screen.getByRole("button")).toBeInTheDocument();
  });

  it("has visible text content", () => {
    render(<Button>Click me!</Button>);
    const button = screen.getByRole("button");
    expect(button).toBeInTheDocument();
    expect(button).not.toBeEmptyDOMElement();
    expect(button).toHaveTextContent(/\S/);
  });
  it("shows the correct text", () => {
    render(<Button>Click me!</Button>);
    expect(screen.getByText(/Click me!/i)).toBeInTheDocument();
  });
});
