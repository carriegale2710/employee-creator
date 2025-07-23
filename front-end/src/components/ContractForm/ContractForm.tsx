import { useFormStatus } from "react-dom";
import Button from "../Button/Button";
import Input from "../Input/Input";
import type { Contract } from "../../services/contracts";
import { NavLink } from "react-router-dom";
import { useState } from "react";
import Select from "../Select/Select";

export interface ContractFormProps {
  prefilled?: Contract | null;
}

const ContractForm = ({ prefilled }: ContractFormProps) => {
  const { pending } = useFormStatus();
  const [isPrefilled, setIsPrefilled] = useState(false);

  // const onSubmit = ()=>{
  //   const userDetails = formData.
  // }

  return (
    <>
      <h2>Contract Form</h2>
      <form
      // action={onSubmit}
      >
        <Button
          onClick={() => {
            setIsPrefilled(!isPrefilled);
          }}
        >
          Use most recent contract as template
        </Button>
        <Input
          label="employeeId"
          type="number"
          value={isPrefilled && prefilled?.employee.id}
        >
          Employee ID
        </Input>
        <Select
          label="department"
          title="Department"
          prefilledValue={isPrefilled ? prefilled?.department : ""}
          type="select"
          options={["ENGINEERING", "SALES", "MARKETING", "DESIGN"]}
        >
          Department
        </Select>
        <Select
          label="contractType"
          title="Contract Type"
          prefilledValue={isPrefilled ? prefilled?.contractType : ""}
          type="select"
          options={["FULL TIME", "PART TIME", "CASUAL", "CONTRACT"]}
        >
          Contract Type
        </Select>
        <Input
          label="salaryAmount"
          type="number"
          value={isPrefilled ? prefilled?.salaryAmount : 0}
        >
          Salary Amount
        </Input>
        <Input
          label="hoursPerWeek"
          type="number"
          value={isPrefilled ? prefilled?.hoursPerWeek : 0}
        >
          Hours per Week
        </Input>
        <Input
          label="startDate"
          type="date"
          value={isPrefilled && prefilled?.startDate}
        >
          Start Date
        </Input>
        <Input
          label="endDate"
          type="date"
          value={isPrefilled && prefilled?.endDate}
        >
          End Date
        </Input>
        <Button type="submit" disabled={pending}>
          {pending ? "Submitting..." : "Submit"}
        </Button>
      </form>
    </>
  );
};

export default ContractForm;
