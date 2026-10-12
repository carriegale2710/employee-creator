import { render, screen } from "@testing-library/react";
import userEvent from "@testing-library/user-event";
import { MemoryRouter, Route, Routes } from "react-router-dom";
import { beforeEach, describe, expect, it, vi } from "vitest";
import EmployeePage from "./EmployeePage";
import { getEmployeeById } from "../services/employees";

vi.mock("../services/employees", async (importOriginal) => {
  const actual =
    await importOriginal<typeof import("../services/employees")>();
  return { ...actual, getEmployeeById: vi.fn() };
});

const mockEmployee = {
  id: 1,
  firstName: "Taylor",
  lastName: "Doe",
  email: "taylor@example.com",
  phone: "123-456-7890",
};
const mockFetch = vi.fn();

const renderEmployeePage = () =>
  render(
    <MemoryRouter initialEntries={["/employees/1"]}>
      <Routes>
        <Route path="/employees/:id" element={<EmployeePage />} />
        <Route path="/employees" element={<p>Employees list</p>} />
      </Routes>
    </MemoryRouter>
  );

describe("EmployeePage delete flow", () => {
  beforeEach(() => {
    vi.clearAllMocks();
    vi.stubGlobal("fetch", mockFetch);
    mockFetch.mockResolvedValue(new Response(null, { status: 204 }));
    vi.spyOn(window, "confirm").mockReturnValue(true);
    vi.mocked(getEmployeeById).mockResolvedValue(mockEmployee);
  });

  it("navigates to the employee list after a successful delete", async () => {
    const user = userEvent.setup();

    renderEmployeePage();

    await user.click(await screen.findByRole("button", { name: /delete employee/i }));

    expect(mockFetch).toHaveBeenCalledWith(
      expect.stringContaining("/employees/1"),
      expect.objectContaining({ method: "DELETE" })
    );
    expect(await screen.findByText("Employees list")).toBeInTheDocument();
  });

  it("shows a delete error when the delete request fails", async () => {
    mockFetch.mockResolvedValueOnce(new Response(null, { status: 500 }));
    const user = userEvent.setup();

    renderEmployeePage();

    await user.click(await screen.findByRole("button", { name: /delete employee/i }));

    expect(await screen.findByRole("alert")).toHaveTextContent(
      "Failed to delete: Failed to delete employee 1"
    );
  });
});
