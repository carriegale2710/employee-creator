import { useState } from "react";
import type { Contract, ContractDTO } from "../services/contracts";
import type { Employee } from "../services/employees";

export const useContractTemplate = (
  reset: (values: ContractDTO) => void,
  employee?: Employee | null,
  prevContract?: Contract | null
) => {
  const [usingTemplate, setUsingTemplate] = useState(false);

  const toggleTemplate = () => {
    const newState = !usingTemplate;
    setUsingTemplate(newState);

    const employeeId = employee?.id || 0;

    if (newState && prevContract) {
      // Apply template values
      const templateData = transformContractToFormData(
        prevContract,
        employeeId
      );
      reset(templateData);
      console.log("Template applied", prevContract);
    } else {
      // Clear form to defaults
      const defaultData = getDefaultContractFormValues(employeeId);
      reset(defaultData);
      console.log("Form reset to blank state");
    }
  };

  return {
    usingTemplate,
    toggleTemplate,
    hasTemplate: prevContract != null,
  };
};

export const transformContractToFormData = (
  contract: Contract,
  employeeId: number
): ContractDTO => ({
  employeeId,
  department: contract.department.replaceAll("_", " "),
  contractType: contract.contractType.replaceAll("_", " "),
  salaryAmount: contract.salaryAmount,
  hoursPerWeek: contract.hoursPerWeek,
  startDate: contract.startDate,
  endDate: contract.endDate || "",
});

export const getDefaultContractFormValues = (
  employeeId: number
): ContractDTO => ({
  employeeId,
  department: "",
  contractType: "",
  salaryAmount: 0,
  hoursPerWeek: 0,
  startDate: "",
  endDate: "",
});
