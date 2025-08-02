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
  defaultValue?: Employee | null;
  //loads in defaultValue values if they exist (editing an existing employee)
  //otherwise it is a blank form for creating a new employee
}

const EmployeeForm = ({ defaultValue }: EmployeeFormProps) => {
  const {
    register,
    handleSubmit,
    formState: { errors, isSubmitting },
  } = useForm<EmployeeDTO>();

  // console.log("Errors:", errors); //check

  const onSubmit = async (formData: EmployeeDTO) => {
    // Handle form submission, e.g., send data to backend

    try {
      //creating employee
      if (!defaultValue) {
        console.log("Creating employee with form data:", formData);
        const result = await createEmployee(formData);
        console.log("Employee successfully created:", result);
        return result;
      }
      //updating employee
      if (defaultValue) {
        console.log(`Updating Employee ID: ${defaultValue.id}`);
        console.log("With form data:", formData);

        const result = await updateEmployee(defaultValue.id, formData);
        console.log("Employee successfully updated:", result);
        return result;
      }
      // todo - redirect or show a success message Toast to user
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
          defaultValue={defaultValue?.firstName ?? ""}
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
          defaultValue={defaultValue?.lastName ?? ""}
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
          defaultValue={defaultValue?.email ?? ""}
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
          defaultValue={defaultValue?.phone ?? ""}
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
          defaultValue={defaultValue?.address ?? ""}
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
