import type { Employee } from "./employees";
import { apiCall } from "./api";

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
  return apiCall<Contract>(
    `/contracts/${id}`,
    "Could not get data for id " + id
  );
};

export const createContract = async (formData: ContractDTO) => {
  return apiCall<Contract>("/contracts", "Failed to create contract", {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(formData),
  });
};
