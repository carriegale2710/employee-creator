import type { Employee } from "./employees";

export const employee1: Employee = {
  id: 1,
  firstName: "Timmy",
  lastName: "Turner",
  email: "timmehhh@example.com",
  phoneNumber: "0400000000",
  address: "123 Fairy Lane",
};

export const employee2: Employee = {
  id: 2,
  firstName: "Cosmo",
  lastName: "Cosma",
  email: "cosmo@example.com",
  phoneNumber: "0400043000",
  address: "123 Fairy Lane",
};

export const mockEmployees: Employee[] = [employee1, employee2];
