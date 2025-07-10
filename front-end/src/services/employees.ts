const API_URL = import.meta.env.VITE_API_URL;
console.log("API:", import.meta.env.VITE_API_URL);

export interface Employee {
  id: number;
  firstName: string;
  lastName: string;
  email: string;
}

export const getAllEmployees = async (): Promise<Employee[]> => {
  const response = await fetch(`${API_URL}/employees`);
  const employees = await response.json();
  return employees;
};

export const getEmployeeById = async (id: string): Promise<Employee> => {
  const response = await fetch(`${API_URL}/employees/${id}`);
  if (!response.ok) {
    throw new Error("Could not get data for id " + id);
  }
  const employee = await response.json();
  return employee;
};
