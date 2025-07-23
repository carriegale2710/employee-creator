import type { Employee } from "./employees";

const API_URL = import.meta.env.VITE_API_URL || "http://localhost:8080";
console.log("API URL:", API_URL);
console.log("Environment mode:", import.meta.env.MODE);

export type Department =
  | "ENGINEERING"
  | "SALES"
  | "DESIGN"
  | "MARKETING"
  | "WHOLESALE";

export type ContractType = "FULL_TIME" | "PART_TIME" | "CASUAL" | "CONTRACT";

export interface Contract {
  id: number;
  employee: Employee;
  department: Department;
  contractType: ContractType;
  salaryAmount: number;
  hoursPerWeek: number;
  startDate: string; // ISO date string
  endDate?: string; // ISO date string or null
}

export interface ContractDTO {
  employeeId: number;
  department?: string;
  contractType: string;
  salaryAmount?: number;
  hoursPerWeek?: number;
  startDate: string; // ISO date string
  endDate?: string; // ISO date string or null
}

export const getContractById = async (id: string): Promise<Contract> => {
  const response = await fetch(`${API_URL}/contracts/${id}`);
  if (!response.ok) {
    throw new Error("Could not get data for id " + id);
  }
  const employee = await response.json();
  return employee;
};

export const createContract = async (formData: ContractDTO) => {
  const response = await fetch(`${API_URL}/contracts`, {
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
