import { useForm } from "react-hook-form";
import Button from "../../components/Button/Button";
import Input from "../../components/Input/Input";
import {
  createContract,
  type Contract,
  type ContractDTO,
} from "../../services/contracts";
import { useEffect, useState } from "react";
import Select from "../../components/Select/Select";

export interface ContractFormProps {
  prevContract?: Contract | null;
}

const ContractForm = ({ prevContract }: ContractFormProps) => {
  const {
    register,
    handleSubmit,
    formState: { errors, isSubmitting },
  } = useForm<ContractDTO>();

  // console.log("Errors:", errors); // Add this to see if validation is blocking submit

  const [usingTemplate, setUsingTemplate] = useState(false);

  useEffect(() => {
    if (!usingTemplate) {
      console.log("Using blank form data");
    }
    if (usingTemplate && prevContract) {
      console.log(
        "Using template from previous contract with ID:",
        prevContract.id,
        "with data:",
        prevContract
      );
    }
  }, [usingTemplate, prevContract]);

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
        {/* Prefilled Values Button if previous contract exists for this employee */}
        {prevContract && (
          <Button
            type="button"
            onClick={() => prevContract && setUsingTemplate(!usingTemplate)}
          >
            {usingTemplate ? "Use Blank Form" : "Use Template"}
          </Button>
        )}

        <Input
          label="employeeId"
          type="number"
          prefilledValue={
            usingTemplate ? String(prevContract?.employee?.id ?? "") : ""
          }
          {...register("employeeId", { required: "Employee ID is required" })}
        >
          Employee ID
          <br />
          {errors.employeeId && (
            <span style={{ color: "red" }}>{errors.employeeId.message}</span>
          )}
        </Input>

        <Select
          label="department"
          title="Department"
          options={["ENGINEERING", "SALES", "MARKETING", "DESIGN"]}
          prefilledValue={usingTemplate ? prevContract?.department ?? "" : ""}
          {...register("department", { required: "Department is required" })}
        >
          Department
          <br />
          {errors.department && (
            <span style={{ color: "red" }}>{errors.department.message}</span>
          )}
        </Select>

        <Select
          label="contractType"
          title="Contract Type"
          options={["FULL TIME", "PART TIME", "CASUAL", "CONTRACT"]}
          prefilledValue={usingTemplate ? prevContract?.contractType : ""}
          {...register("contractType", {
            required: "Contract Type is required",
          })}
        >
          Contract Type
          <br />
          {errors.contractType && (
            <span style={{ color: "red" }}>{errors.contractType.message}</span>
          )}
        </Select>

        <Input
          label="salaryAmount"
          type="number"
          prefilledValue={
            usingTemplate ? String(prevContract?.salaryAmount ?? "") : ""
          }
          {...register("salaryAmount", {
            required: "Salary Amount is required",
          })}
        >
          Salary Amount
          <br />
          {errors.salaryAmount && (
            <span style={{ color: "red" }}>{errors.salaryAmount.message}</span>
          )}
        </Input>

        <Input
          label="hoursPerWeek"
          type="number"
          prefilledValue={
            usingTemplate ? String(prevContract?.hoursPerWeek ?? "") : ""
          }
          {...register("hoursPerWeek", {
            required: "Hours per Week is required",
          })}
        >
          Hours per Week
          <br />
          {errors.hoursPerWeek && (
            <span style={{ color: "red" }}>{errors.hoursPerWeek.message}</span>
          )}
        </Input>

        <Input
          label="startDate"
          type="date"
          prefilledValue={usingTemplate ? prevContract?.startDate ?? "" : ""}
          {...register("startDate", { required: "Start Date is required" })}
        >
          Start Date
          <br />
          {errors.startDate && (
            <span style={{ color: "red" }}>{errors.startDate.message}</span>
          )}
        </Input>

        <Input
          label="endDate"
          type="date"
          prefilledValue={usingTemplate ? prevContract?.endDate ?? "" : ""}
          {...register("endDate", { required: false })} // Optional field
        >
          End Date
          <br />
          {errors.endDate && (
            <span style={{ color: "red" }}>{errors.endDate.message}</span>
          )}
        </Input>

        <Button type="submit" disabled={isSubmitting}>
          {isSubmitting ? "Submitting..." : "Submit"}
        </Button>
      </form>
    </>
  );
};

export default ContractForm;
