import { useFormStatus } from "react-dom";
import type { Employee } from "../../services/employees";
import Button from "../Button/Button";
import Input from "./Input";

export interface FormProps {
  prefilled?: Employee | null;
}

const Form = ({ prefilled }: FormProps) => {
  const { pending } = useFormStatus();

  // const onSubmit = ()=>{
  //   const userDetails = formData.
  // }

  return (
    <form
    // action={onSubmit}
    >
      <h2>Employee Details</h2>
      <Input label="firstName" type="text" value={prefilled?.firstName}>
        First Name
      </Input>
      <Input label="lastName" type="text" value={prefilled?.lastName}>
        Last Name
      </Input>
      <Input label="email" type="email" value={prefilled?.email}>
        Email
      </Input>
      <Input label="address" type="search" value={prefilled?.address}>
        Address
      </Input>
      <Input label="phone" type="number" value={prefilled?.phoneNumber}>
        Phone
      </Input>
      <Input label="dob" type="date" value={prefilled?.dateOfBirth}>
        Date of birth
      </Input>
      <Input label="tfn" type="number" value={prefilled?.taxFileNumber}>
        Tax File Number
      </Input>
      <Button type="submit" disabled={pending}>
        {pending ? "Submitting..." : "Submit"}
      </Button>
    </form>
  );
};

export default Form;
