import { useForm } from "react-hook-form";
import Button from "../../components/Button/Button";
import Input from "../../components/Input/Input";
import {
  createContract,
  type Contract,
  type CreateContractDTO,
} from "../../services/contracts";
import { useState } from "react";
import Select from "../../components/Select/Select";

export interface ContractFormProps {
  prevContract?: Contract | null;
}

const ContractForm = ({ prevContract }: ContractFormProps) => {
  const {
    register,
    handleSubmit,
    formState: { errors, isSubmitting },
  } = useForm<CreateContractDTO>();

  // console.log("Errors:", errors); // Add this to see if validation is blocking submit

  const [usingTemplate, setUsingTemplate] = useState(false);

  const onSubmit = async (formData: CreateContractDTO) => {
    // Handle form submission, e.g., send data to backend
    try {
      console.log("Form Data:", formData);
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
            onClick={() => prevContract && setUsingTemplate((prev) => !prev)}
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
          {...register("employeeId", { required: true })}
        >
          Employee ID
        </Input>
        {errors.employeeId && (
          <span style={{ color: "red" }}>Employee ID is required</span>
        )}

        <Select
          label="department"
          title="Department"
          options={["ENGINEERING", "SALES", "MARKETING", "DESIGN"]}
          prefilledValue={usingTemplate ? prevContract?.department ?? "" : ""}
          {...register("department", { required: true })}
        >
          Department
        </Select>
        {errors.department && (
          <span style={{ color: "red" }}>Department is required</span>
        )}

        <Select
          label="contractType"
          title="Contract Type"
          options={["FULL TIME", "PART TIME", "CASUAL", "CONTRACT"]}
          prefilledValue={usingTemplate ? prevContract?.contractType : ""}
          {...register("contractType", { required: true })}
        >
          Contract Type
        </Select>
        {errors.contractType && (
          <span style={{ color: "red" }}>Contract Type is required</span>
        )}

        <Input
          label="salaryAmount"
          type="number"
          prefilledValue={
            usingTemplate ? String(prevContract?.salaryAmount ?? "") : ""
          }
          {...register("salaryAmount", { required: true })}
        >
          Salary Amount
        </Input>
        {errors.salaryAmount && (
          <span style={{ color: "red" }}>Salary Amount is required</span>
        )}

        <Input
          label="hoursPerWeek"
          type="number"
          prefilledValue={
            usingTemplate ? String(prevContract?.hoursPerWeek ?? "") : ""
          }
          {...register("hoursPerWeek", { required: true })}
        >
          Hours per Week
        </Input>
        {errors.hoursPerWeek && (
          <span style={{ color: "red" }}>Hours per Week is required</span>
        )}

        <Input
          label="startDate"
          type="date"
          prefilledValue={usingTemplate ? prevContract?.startDate ?? "" : ""}
          {...register("startDate", { required: true })}
        >
          Start Date
        </Input>
        {errors.startDate && (
          <span style={{ color: "red" }}>Start Date is required</span>
        )}

        <Input
          label="endDate"
          type="date"
          prefilledValue={usingTemplate ? prevContract?.endDate ?? "" : ""}
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
