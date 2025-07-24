import type { Contract } from "./contracts";

const API_URL = import.meta.env.VITE_API_URL || "http://localhost:8080";
console.log("API URL:", API_URL);
console.log("Environment mode:", import.meta.env.MODE);

export interface Employee {
  id: number;
  firstName: string;
  lastName: string;
  email: string;
  phone: string;
  address?: string;
  contracts?: Contract[];
}

export interface EmployeeDTO {
  firstName?: string;
  lastName?: string;
  email?: string;
  phone?: string;
  address?: string;
}

export const getAllEmployees = async (): Promise<Employee[]> => {
  const response = await fetch(`${API_URL}/employees`);
  const employees = await response.json();
  return employees;
};

export const getEmployeeById = async (id: number): Promise<Employee> => {
  const response = await fetch(`${API_URL}/employees/${id}`);
  if (!response.ok) {
    throw new Error("Could not get data for id " + id);
  }
  const employee = await response.json();
  console.log("Fetched employee:", employee);
  return employee;
};

export const createEmployee = async (formData: EmployeeDTO) => {
  const response = await fetch(`${API_URL}/employees`, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(formData),
  });
  if (!response.ok) {
    throw new Error("Failed to create employee");
  }
  const result = await response.json();
  return result;
};

export const updateEmployee = async (id: number, formData: EmployeeDTO) => {
  const response = await fetch(`${API_URL}/employees/${id}`, {
    method: "PATCH",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(formData),
  });
  if (!response.ok) {
    throw new Error("Failed to update employee");
  }
  const result = await response.json();
  return result;
};

export const deleteEmployee = async (id: number) => {
  const response = await fetch(`${API_URL}/employees/${id}`, {
    method: "DELETE",
  });
  if (!response.ok) {
    throw new Error("delete fetch failed for employee ID " + id);
  }
  return true; // Return true on successful deletion
};
