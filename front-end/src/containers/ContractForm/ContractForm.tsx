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

export interface ContractFormProps {
  employee?: Employee | null; // Optional employee prop for context
}

const ContractForm = ({ employee }: ContractFormProps) => {
  const navigate = useNavigate();

  const {
    register,
    handleSubmit,
    getValues,
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
    }
  };

  return (
    <>
      <h2>Contract Form</h2>
      <form onSubmit={handleSubmit(onSubmit)}>
        <Input
          errors={errors.employeeId}
          label="employeeId"
          type="number"
          {...register("employeeId", {
            required: "Employee ID is required",
            min: 1,
            max: 999,
          })}
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
          {...register("department", { required: false })}
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
            min: { value: 0, message: "Salary must be positive" },
            max: { value: 500000, message: "Salary exceeds maximum" },
          })}
        >
          Salary Amount
        </Input>

        <Input
          errors={errors.hoursPerWeek}
          label="hoursPerWeek"
          type="number"
          {...register("hoursPerWeek", {
            required: false, // Optional field
            min: { value: 0, message: "Hours must be positive" },
            max: { value: 40, message: "Cannot exceed 40 hours per week" },
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
          {...register("endDate", {
            required: false,
            // Optional field, can be left blank
            validate: (value) => {
              if (value && new Date(value) < new Date(getValues("startDate"))) {
                return "End Date cannot be before Start Date";
              }
              return true;
            },
          })}
        >
          End Date
        </Input>

        <div
          style={{
            display: "flex",
            gap: "10px",
            marginTop: "20px",
            justifyContent: "space-between",
          }}
        >
          <NavLink to="/employees">
            <Button
              style={{ backgroundColor: "lightgray", color: "black" }}
              type="button"
              onClick={() => console.log("Cancel button clicked")}
            >
              Cancel
            </Button>
          </NavLink>
          <Button type="submit" disabled={isSubmitting}>
            {isSubmitting ? "Submitting..." : "Submit"}
          </Button>
        </div>
      </form>
    </>
  );
};

export default ContractForm;
