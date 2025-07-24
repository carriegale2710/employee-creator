const API_URL = "http://localhost:8080/contracts";
console.log("API:", API_URL);

export interface Contract {
  id: number;
  employeeId: number;
  departmentId?: number;
  contractType: "FULL_TIME" | "PART_TIME";
  salaryAmount?: number;
  hoursPerWeek?: number;
  startDate: string; // ISO date string
  endDate?: string; // ISO date string or null
  isActive: boolean;
}
