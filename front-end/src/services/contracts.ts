import type { Employee } from "./employees";

const API_URL = "http://localhost:8080/contracts";
console.log("API:", API_URL);

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
  department?: Department;
  contractType: ContractType;
  salaryAmount?: number;
  hoursPerWeek?: number;
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

export const getAllContracts = async (): Promise<Contract[]> => {
  const response = await fetch(`${API_URL}/contracts`);
  const contracts = await response.json();
  return contracts;
};

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
    body: JSON.stringify(formData),
  });
  if (!response.ok) {
    throw new Error("Failed to create employee");
  }
  const result = await response.json();
  return result;
};
