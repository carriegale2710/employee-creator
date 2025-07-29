import type { Employee } from "./employees";

const API_URL = import.meta.env.VITE_API_URL || "http://localhost:8080";
console.log("API URL (contracts):", API_URL);

export type Department =
  | "ENGINEERING"
  | "SALES"
  | "DESIGN"
  | "MARKETING"
  | "WHOLESALE";

export const DEPARTMENTS: Department[] = [
  "ENGINEERING",
  "SALES",
  "DESIGN",
  "MARKETING",
  "WHOLESALE",
];

export type ContractType = "FULL TIME" | "PART TIME" | "CASUAL" | "CONTRACT";

export const CONTRACT_TYPES: ContractType[] = [
  "FULL TIME",
  "PART TIME",
  "CASUAL",
  "CONTRACT",
];

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
    throw new Error("Failed to create contract");
  }
  const result = await response.json();
  return result;
};
