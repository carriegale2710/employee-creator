import type { Contract } from "./contracts";
import { employee1, employee2 } from "./mockEmployees";

export const contract1: Contract = {
  id: 1,
  employee: employee1,
  department: "ENGINEERING",
  contractType: "FULL TIME",
  salaryAmount: 85000.0,
  hoursPerWeek: 38,
  startDate: "2025-03-23",
  endDate: "2026-03-23",
};

export const contract2: Contract = {
  id: 1,
  employee: employee2,
  contractType: "FULL TIME",
  department: "SALES",
  salaryAmount: 95000.0,
  hoursPerWeek: 38,
  startDate: "2025-03-01",
  endDate: "2026-03-01",
};

export const mockContracts: Contract[] = [contract1, contract2];

export const missingFieldsContract: Contract = {
  id: 76,
  employee: employee1,
  contractType: "FULL TIME",
  department: "SALES",
  salaryAmount: -50000.0,
  hoursPerWeek: 0,
  startDate: "2027-03-23",
  endDate: "2025-03-23",
};

export const invalidContract: Contract = {
  id: 76,
  employee: employee1,
  contractType: "FULL TIME",
  department: "SALES",
  salaryAmount: -50000.0,
  hoursPerWeek: 0,
  startDate: "2027-03-23",
  endDate: "2025-03-23",
};
