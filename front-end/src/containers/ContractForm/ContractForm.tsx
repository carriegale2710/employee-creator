import { useForm } from "react-hook-form";
import Button from "../../components/Button/Button";
import Input from "../../components/Input/Input";
import {
  CONTRACT_TYPES,
  createContract,
  DEPARTMENTS,
  type Contract,
  type ContractDTO,
} from "../../services/contracts";
import Select from "../../components/Select/Select";
import type { Employee } from "../../services/employees";
import { useContractTemplate } from "../../hooks/useContractTemplate";

export interface ContractFormProps {
  employee?: Employee | null; // Optional employee prop for context
  prevContract?: Contract | null;
}

const ContractForm = ({ employee, prevContract }: ContractFormProps) => {
  const {
    register,
    handleSubmit,
    reset,
    formState: { errors, isSubmitting },
  } = useForm<ContractDTO>({
    defaultValues: {
      employeeId: employee?.id || 0,
    },
  });

  const { usingTemplate, toggleTemplate, hasTemplate } = useContractTemplate(
    reset,
    employee,
    prevContract
  );

  const onSubmit = async (formData: ContractDTO) => {
    // Handle form submission, e.g., send data to backend
    try {
      console.log("Creating new Contract:", formData);
      const result = await createContract(formData);
      console.log("Contract created:", result);
      return result;
    } catch (error) {
      console.error(error);
    }
  };

  return (
    <>
      <h2>Contract Form</h2>
      <form onSubmit={handleSubmit(onSubmit)}>
        {/* Template Button if previous contract exists for this employee */}
        {hasTemplate && (
          <Button type="button" onClick={toggleTemplate}>
            {usingTemplate
              ? "Use Blank Form"
              : "Duplicate Most Recent Contract"}
          </Button>
        )}

        <Input
          errors={errors.employeeId}
          label="employeeId"
          type="number"
          {...register("employeeId", { required: "Employee ID is required" })}
        >
          Employee ID
        </Input>

        <Select
          errors={errors.department}
          label="department"
          title="Department"
          options={DEPARTMENTS.map((type) =>
            type.toUpperCase().split("_").join(" ")
          )}
          {...register("department", { required: "Department is required" })}
        >
          Department
        </Select>

        <Select
          errors={errors.contractType}
          label="contractType"
          title="Contract Type"
          options={CONTRACT_TYPES.map((type) =>
            type.toUpperCase().split("_").join(" ")
          )}
          {...register("contractType", {
            required: "Contract Type is required",
          })}
        >
          Contract Type
        </Select>

        <Input
          errors={errors.salaryAmount}
          label="salaryAmount"
          type="number"
          {...register("salaryAmount", {
            required: "Salary Amount is required",
          })}
        >
          Salary Amount
        </Input>

        <Input
          errors={errors.hoursPerWeek}
          label="hoursPerWeek"
          type="number"
          {...register("hoursPerWeek", {
            required: "Hours per Week is required",
          })}
        >
          Hours per Week
        </Input>

        <Input
          errors={errors.startDate}
          label="startDate"
          type="date"
          {...register("startDate", { required: "Start Date is required" })}
        >
          Start Date
        </Input>

        <Input
          errors={errors.endDate}
          label="endDate"
          type="date"
          {...register("endDate", { required: false })} // Optional field
        >
          End Date
        </Input>

        <Button type="submit" disabled={isSubmitting}>
          {isSubmitting ? "Submitting..." : "Submit"}
        </Button>
      </form>
    </>
  );
};

export default ContractForm;
