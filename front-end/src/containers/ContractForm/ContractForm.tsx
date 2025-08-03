import { useForm } from "react-hook-form";
import Button from "../../components/Button/Button";
import Input from "../../components/Input/Input";
import {
  CONTRACT_TYPES,
  createContract,
  DEPARTMENTS,
  type ContractDTO,
} from "../../services/contracts";
import Select from "../../components/Select/Select";
import type { Employee } from "../../services/employees";
import { NavLink, useNavigate } from "react-router-dom";
import { useValidation } from "../../hooks/useValidation";
import { useState } from "react";

export interface ContractFormProps {
  employee?: Employee | null; // Optional employee prop for context
}

const ContractForm = ({ employee }: ContractFormProps) => {
  const navigate = useNavigate();
  const [submitError, setSubmitError] = useState<string>("");
  const { contractValidation } = useValidation();

  const {
    register,
    handleSubmit,
    formState: { errors, isSubmitting },
  } = useForm<ContractDTO>({
    defaultValues: {
      employeeId: employee?.id || 0,
    },
  });

  const onSubmit = async (formData: ContractDTO) => {
    // Handle form submission, e.g., send data to backend
    try {
      console.info("Creating new Contract:", formData);
      const result = await createContract(formData);
      console.info("Contract created:", result);
      navigate(`/employees/${employee?.id}/contracts`);
    } catch (error) {
      console.error(error);
      setSubmitError(`${error}`);
    } finally {
      console.info("Form submission complete.");
    }
  };

  return (
    <form onSubmit={handleSubmit(onSubmit)}>
      <h2>Contract Form</h2>
      <Input
        errors={errors.employeeId}
        label="employeeId"
        type="number"
        {...register("employeeId", contractValidation.employeeId)}
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
        {...register("department", contractValidation.department)}
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
        {...register("contractType", contractValidation.contractType)}
      >
        Contract Type
      </Select>

      <Input
        errors={errors.salaryAmount}
        label="salaryAmount"
        type="number"
        {...register("salaryAmount", contractValidation.salaryAmount)}
      >
        Salary Amount
      </Input>

      <Input
        errors={errors.hoursPerWeek}
        label="hoursPerWeek"
        type="number"
        {...register("hoursPerWeek", contractValidation.hoursPerWeek)}
      >
        Hours per Week
      </Input>

      <Input
        errors={errors.startDate}
        label="startDate"
        type="date"
        {...register("startDate", contractValidation.startDate)}
      >
        Start Date
      </Input>

      <Input
        errors={errors.endDate}
        label="endDate"
        type="date"
        {...register("endDate", contractValidation.endDate)}
      >
        End Date
      </Input>

      <div className="flex justify-end gap-2 mt-5">
        <NavLink to="/employees">
          <Button
            className="bg-gray-200 text-black"
            type="button"
            onClick={() => console.log("Cancel button clicked")}
          >
            Cancel
          </Button>
        </NavLink>
        <Button type="submit" disabled={isSubmitting} variant="submit">
          {isSubmitting ? "Submitting..." : "Submit"}
        </Button>
      </div>

      {submitError && (
        <p className="text-red-600 font-bold italic block">
          An error occurred while submitting the form. Please try again.
          <br />
          {submitError}
        </p>
      )}
    </form>
  );
};

export default ContractForm;
