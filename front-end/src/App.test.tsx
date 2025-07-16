import { describe, expect, it } from "vitest";
import { render, screen } from "@testing-library/react";
import App from "./App";

describe("Smoke test", () => {
  it("checks true", () => {
    expect(true).toBe(true);
  });
});

describe("Page features", () => {
  it("renders page info", () => {
    render(<App />);
    expect(
      screen.getByText(/Click on "Edit" to see more about each employee./i)
    );
  });

  it("renders add employee button", () => {
    render(<App />);
    expect(screen.getByText(/Add Employee/i)); //Button
  });
});

describe("Employee List", () => {
  it("renders employee list", () => {
    render(<App />);
    expect(screen.getByText(/Employee List/i)); //Button
  });
});
