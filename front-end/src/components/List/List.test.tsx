import { render, screen } from "@testing-library/react";
import { MemoryRouter } from "react-router-dom";
import { describe, expect, it } from "vitest";
import "@testing-library/jest-dom";
import List from "./List";
import { mockEmployees } from "../../services/mockEmployees";

describe("List", () => {
  it("Renders a Card with text content", async () => {
    render(
      <MemoryRouter>
        <List employees={mockEmployees} />
      </MemoryRouter>
    );
    const cards = await screen.findAllByTestId("card");
    expect(cards).toBeTruthy();
    expect(cards).toHaveLength(mockEmployees.length);
    expect(cards[0]).toHaveTextContent(/\S/);
    expect(cards[0]).toHaveTextContent(mockEmployees[0].email);
  });
});
