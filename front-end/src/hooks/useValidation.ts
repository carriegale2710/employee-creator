import type { ContractDTO } from "../services/contracts";

export const useValidation = () => {
  const employeeValidation = {
    firstName: {
      required: "*required",
      maxLength: {
        value: 20,
        message: "First Name must be at most 20 characters",
      },
      pattern: {
        value: /^[A-Za-z]+$/,
        message: "First Name must contain only letters",
      },
    },
    lastName: {
      required: "*required",
      maxLength: {
        value: 20,
        message: "Last Name must be at most 20 characters",
      },
      pattern: {
        value: /^[A-Za-zÀ-ÖØ-öø-ÿ''\- ]+$/,
        message: "Last Name must contain only letters, apostrophes, or accents",
      },
    },
    email: {
      required: "*required",
      pattern: {
        value: /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/,
        message: "Invalid email format",
      },
    },
    phone: {
      required: "*required",
      pattern: {
        value: /^[0-9+]{8,12}$/,
        message: "Phone must be 8-12 digits long",
      },
    },
    address: {
      maxLength: {
        value: 100,
        message: "Address must be at most 100 characters",
      },
    },
  };

  const contractValidation = {
    employeeId: {
      required: "*required",
      min: 1,
      max: 500,
    },
    department: {
      required: false,
    },
    contractType: {
      required: "*required",
    },
    salaryAmount: {
      required: "*required",
      min: { value: 0, message: "Salary must be positive" },
      max: { value: 500000, message: "Salary exceeds maximum" },
    },
    hoursPerWeek: {
      required: "*required",
      min: { value: 0, message: "Hours must be positive" },
      max: { value: 40, message: "Cannot exceed 40 hours per week" },
    },
    startDate: {
      required: "*required",
    },
    endDate: {
      required: false,
      validate: (value: string | undefined, formValues: ContractDTO) => {
        if (
          value &&
          formValues.startDate &&
          new Date(value) < new Date(formValues.startDate)
        ) {
          return "End Date cannot be before Start Date";
        }
        return true;
      },
    },
  };

  return { employeeValidation, contractValidation };
};
