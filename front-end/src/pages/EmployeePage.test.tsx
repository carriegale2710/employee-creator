import { render, screen } from "@testing-library/react";
import userEvent from "@testing-library/user-event";
import { MemoryRouter, Route, Routes } from "react-router-dom";
import { beforeEach, describe, expect, it, vi } from "vitest";
import EmployeePage from "./EmployeePage";
import { deleteEmployee, getEmployeeById } from "../services/employees";

vi.mock("../services/employees", () => ({
  getEmployeeById: vi.fn(),
  deleteEmployee: vi.fn(),
}));

const mockEmployee = {
  id: 1,
  firstName: "Taylor",
  lastName: "Doe",
  email: "taylor@example.com",
  phone: "123-456-7890",
};

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
    vi.spyOn(window, "confirm").mockReturnValue(true);
    vi.mocked(getEmployeeById).mockResolvedValue(mockEmployee);
  });

  it("navigates to the employee list after a successful delete", async () => {
    vi.mocked(deleteEmployee).mockResolvedValueOnce(undefined);
    const user = userEvent.setup();

    renderEmployeePage();

    await user.click(await screen.findByRole("button", { name: /delete employee/i }));

    expect(deleteEmployee).toHaveBeenCalledWith(1);
    expect(await screen.findByText("Employees list")).toBeInTheDocument();
  });

  it("shows a delete error when the delete request fails", async () => {
    vi.mocked(deleteEmployee).mockRejectedValueOnce(new Error("delete failed"));
    const user = userEvent.setup();

    renderEmployeePage();

    await user.click(await screen.findByRole("button", { name: /delete employee/i }));

    expect(deleteEmployee).toHaveBeenCalledWith(1);
    expect(await screen.findByRole("alert")).toHaveTextContent(
      "Failed to delete: delete failed"
    );
  });
});
