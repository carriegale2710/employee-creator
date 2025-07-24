import {
  createEmployee,
  updateEmployee,
  type Employee,
  type EmployeeDTO,
} from "../../services/employees";
import Button from "../../components/Button/Button";
import Input from "../../components/Input/Input";
import { useForm } from "react-hook-form";

export interface EmployeeFormProps {
  prefilled?: Employee | null;
  //loads in prefilled values if they exist (editing an existing employee)
  //otherwise it is a blank form for creating a new employee
}

const EmployeeForm = ({ prefilled }: EmployeeFormProps) => {
  const {
    register,
    handleSubmit,
    formState: { errors, isSubmitting },
  } = useForm<EmployeeDTO>();

  console.log("Errors:", errors); //check

  const onSubmit = async (formData: EmployeeDTO) => {
    // Handle form submission, e.g., send data to backend
    try {
      console.log("Form Data:", formData);
      const result = await (prefilled
        ? updateEmployee(prefilled.id, formData)
        : createEmployee(formData));
      console.log("Employee created:", result);
      return result;
    } catch (error) {
      console.error(error);
    }
  };

  return (
    <>
      <h2>Employee Form</h2>
      <form
        onSubmit={handleSubmit(onSubmit)}
        className="employee-form"
        autoComplete="off"
      >
        <Input
          label="firstName"
          type="text"
          prefilledValue={prefilled?.firstName ?? ""}
          {...register("firstName", { required: true })}
        >
          First Name
        </Input>
        {errors.firstName && (
          <span style={{ color: "red" }}>First Name is required</span>
        )}

        <Input
          label="lastName"
          type="text"
          prefilledValue={prefilled?.lastName ?? ""}
          {...register("lastName", { required: true })}
        >
          Last Name
        </Input>
        {errors.lastName && (
          <span style={{ color: "red" }}>Last Name is required</span>
        )}

        <Input
          label="email"
          type="email"
          prefilledValue={prefilled?.email ?? ""}
          {...register("email", { required: true })}
        >
          Email
        </Input>
        {errors.email && (
          <span style={{ color: "red" }}>Email is required</span>
        )}

        <Input
          label="phone"
          type="tel"
          prefilledValue={prefilled?.phone ?? ""}
          {...register("phone", { required: true })}
        >
          Phone
        </Input>
        {errors.phone && (
          <span style={{ color: "red" }}>Phone is required</span>
        )}

        <Input
          label="address"
          type="search"
          prefilledValue={prefilled?.address ?? ""}
          {...register("address")}
        >
          Address
        </Input>

        <Button type="submit" disabled={isSubmitting}>
          {isSubmitting ? "Submitting..." : "Submit"}
        </Button>
      </form>
    </>
  );
};

export default EmployeeForm;
