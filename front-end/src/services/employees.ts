import { apiCall } from "./api";

export type EmployeeStatus =
  | "ACTIVE"
  | "INACTIVE"
  | "NEW_HIRE"
  | "TERMINATED"
  | "ON_LEAVE";

export interface Employee {
  id: number;
  firstName: string;
  lastName: string;
  email: string;
  phone: string;
  address?: string;
  status?: EmployeeStatus;
  contractEndDate?: string; // ISO date string
  contractCount?: number; // Total number of contracts
}

export interface EmployeeDTO {
  firstName?: string;
  lastName?: string;
  email?: string;
  phone?: string;
  address?: string;
}

export const getAllEmployees = async (): Promise<Employee[]> => {
  return apiCall<Employee[]>("/employees", `Failed to get data for employees`);
};

export const getEmployeeById = async (id: number): Promise<Employee> => {
  return apiCall<Employee>(
    `/employees/${id}`,
    `Could not get data for id ${id}`
  );
};

export const createEmployee = async (formData: EmployeeDTO) => {
  return apiCall<Employee>("/employees", "Failed to create employee", {
    method: "POST",
    body: JSON.stringify(formData),
  });
};

export const updateEmployee = async (id: number, formData: EmployeeDTO) => {
  return apiCall<Employee>(`/employees/${id}`, "Failed to update employee", {
    method: "PATCH",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(formData),
  });
};

export const deleteEmployee = async (id: number) => {
  apiCall<void>(`/employees/${id}`, "Failed to delete employee" + id, {
    method: "DELETE",
  });
  return true; //successful deletion
};
