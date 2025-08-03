import {
  createEmployee,
  updateEmployee,
  type Employee,
  type EmployeeDTO,
} from "../../services/employees";
import Button from "../../components/Button/Button";
import Input from "../../components/Input/Input";
import { useForm } from "react-hook-form";
import { NavLink, useNavigate } from "react-router-dom";
import { useState } from "react";
import { useValidation } from "../../hooks/useValidation";

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
  const navigate = useNavigate();
  const [submitError, setSubmitError] = useState<string>("");
  const { employeeValidation } = useValidation();

  const onSubmit = async (formData: EmployeeDTO) => {
    // Handle form submission, send data to backend

    try {
      //creating employee
      if (!defaultValue) {
        console.info("Creating employee with form data:", formData);
        const created = await createEmployee(formData);
        console.info("Employee successfully created:", created);
        navigate(`/employees/${created.id}/contracts/new`);
        return created;
      }
      //updating employee
      if (defaultValue) {
        console.info(`Updating Employee ID: ${defaultValue.id}`);
        console.info("With form data:", formData);

        const edited = await updateEmployee(defaultValue.id, formData);
        console.info("Employee successfully updated:", edited);
        navigate(`/employees/${edited.id}`);
        return edited;
      }
      // todo - redirect or show a success message Toast to user
    } catch (error) {
      console.error(error);
      setSubmitError(`${error}`);
    } finally {
      console.info("Form submission complete.");
    }
  };

  return (
    <form onSubmit={handleSubmit(onSubmit)} className="max-w-3xl mx-auto p-6">
      <h2 className="text-lg font-bold mb-4 text-center">Employee Form</h2>

      <div className="mt-10 grid grid-cols-1 gap-x-6 gap-y-8 sm:grid-cols-6">
        <Input
          errors={errors.firstName}
          label="firstName"
          type="text"
          defaultValue={defaultValue?.firstName ?? ""}
          {...register("firstName", employeeValidation.firstName)}
        >
          First Name
        </Input>

        <Input
          errors={errors.lastName}
          label="lastName"
          type="text"
          defaultValue={defaultValue?.lastName ?? ""}
          {...register("lastName", employeeValidation.lastName)}
        >
          Last Name
        </Input>
      </div>

      <Input
        errors={errors.email}
        label="email"
        type="email"
        defaultValue={defaultValue?.email ?? ""}
        {...register("email", employeeValidation.email)}
      >
        Email
      </Input>

      <Input
        errors={errors.phone}
        label="phone"
        type="text"
        defaultValue={defaultValue?.phone.replaceAll(" ", "") ?? ""}
        {...register("phone", employeeValidation.phone)}
      >
        Phone
      </Input>

      <Input
        label="address"
        type="search"
        defaultValue={defaultValue?.address ?? ""}
        {...register("address", employeeValidation.address)}
      >
        Address
      </Input>
      <div className="flex justify-end gap-4 mt-6">
        <NavLink to="/employees">
          <Button
            variant="secondary"
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

export default EmployeeForm;
