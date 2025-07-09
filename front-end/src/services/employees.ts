export interface Employee {
  id: number;
  firstName: string;
  lastName: string;
  email: string;
}

export const getAllEmployees = async (): Promise<Employee[]> => {
  const response = await fetch("http://localhost:8080/employees");
  const employees = await response.json();
  return employees;
};

export const getEmployeeById = async (id: string): Promise<Employee> => {
  const response = await fetch("http://localhost:8080/employees/" + id);
  if (!response.ok) {
    throw new Error("Could not get data for id " + id);
  }
  const employee = await response.json();
  return employee;
};
