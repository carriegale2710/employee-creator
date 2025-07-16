import type { Employee } from "../../services/employees";
import Input from "./Input";

export interface FormProps {
  prefilled?: Employee | null;
}

const Form = ({ prefilled }: FormProps) => {
  return (
    <form>
      <h2>Employee Details</h2>
      <Input label="firstName" type="text" placeholder={prefilled?.firstName}>
        First Name
      </Input>
      <Input label="lastName" type="text" placeholder={prefilled?.lastName}>
        Last Name
      </Input>
      <Input label="email" type="email" placeholder={prefilled?.email}>
        Email
      </Input>
      <Input label="address" type="search" placeholder={prefilled?.address}>
        Address
      </Input>
      <Input label="phone" type="number" placeholder={prefilled?.phoneNumber}>
        Phone
      </Input>
      <Input label="dob" type="date" placeholder={prefilled?.dateOfBirth}>
        Date of birth
      </Input>
      <Input label="tfn" type="number" placeholder={prefilled?.taxFileNumber}>
        Tax File Number
      </Input>
    </form>
  );
};

export default Form;
