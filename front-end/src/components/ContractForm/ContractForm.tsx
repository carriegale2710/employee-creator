import { useFormStatus } from "react-dom";
import Button from "../Button/Button";
import Input from "../Input/Input";
import type { Contract } from "../../services/contracts";

export interface ContractFormProps {
  prefilled?: Contract | null;
}

const ContractForm = ({ prefilled }: ContractFormProps) => {
  const { pending } = useFormStatus();

  // const onSubmit = ()=>{
  //   const userDetails = formData.
  // }

  return (
    <>
      <form
      // action={onSubmit}
      >
        <Input label="employeeId" type="number" value={prefilled?.employee.id}>
          Employee ID
        </Input>
        <Input label="department" type="select" value={prefilled?.department}>
          Department
        </Input>
        <Input
          label="contractType"
          type="select"
          value={prefilled?.contractType}
        >
          Contract Type
        </Input>
        <Input
          label="salaryAmount"
          type="number"
          value={prefilled?.salaryAmount}
        >
          Salary Amount
        </Input>
        <Input
          label="hoursPerWeek"
          type="number"
          value={prefilled?.hoursPerWeek}
        >
          Hours per Week
        </Input>
        <Input label="startDate" type="date" value={prefilled?.startDate}>
          Start Date
        </Input>
        <Input label="endDate" type="date" value={prefilled?.endDate}>
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
