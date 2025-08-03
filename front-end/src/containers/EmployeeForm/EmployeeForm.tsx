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

  const onSubmit = async (formData: EmployeeDTO) => {
    // Handle form submission, send data to backend

    try {
      //creating employee
      if (!defaultValue) {
        console.info("Creating employee with form data:", formData);
        const result = await createEmployee(formData);
        console.info("Employee successfully created:", result);
        navigate(`/employees`);
        return result;
      }
      //updating employee
      if (defaultValue) {
        console.info(`Updating Employee ID: ${defaultValue.id}`);
        console.info("With form data:", formData);

        const result = await updateEmployee(defaultValue.id, formData);
        console.info("Employee successfully updated:", result);
        navigate(`/employees/${defaultValue.id}`);
        return result;
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
    <form onSubmit={handleSubmit(onSubmit)} autoComplete="on">
      <h2>Employee Form</h2>
      <Input
        errors={errors.firstName}
        label="firstName"
        type="text"
        defaultValue={defaultValue?.firstName ?? ""}
        {...register("firstName", {
          required: "*required",
          maxLength: {
            value: 20,
            message: "First Name must be at most 20 characters",
          },
          pattern: {
            value: /^[A-Za-z]+$/,
            message: "First Name must contain only letters",
          },
        })}
      >
        First Name
      </Input>

      <Input
        errors={errors.lastName}
        label="lastName"
        type="text"
        defaultValue={defaultValue?.lastName ?? ""}
        {...register("lastName", {
          required: "*required",
          maxLength: {
            value: 20,
            message: "Last Name must be at most 20 characters",
          },
          pattern: {
            value: /^[A-Za-zÀ-ÖØ-öø-ÿ'’\- ]+$/,
            message:
              "Last Name must contain only letters, apostrophes, or accents",
          },
        })}
      >
        Last Name
      </Input>

      <Input
        errors={errors.email}
        label="email"
        type="email"
        defaultValue={defaultValue?.email ?? ""}
        {...register("email", {
          required: "*required",
          pattern: {
            value: /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/,
            message: "Invalid email format",
          },
        })}
      >
        Email
      </Input>

      <Input
        errors={errors.phone}
        label="phone"
        type="tel"
        defaultValue={defaultValue?.phone.replaceAll(" ", "") ?? ""}
        {...register("phone", {
          required: "*required",
          pattern: {
            value: /^[0-9+]{8,12}$/,
            message: "Phone must be 8-12 digits long",
          },
        })}
      >
        Phone
      </Input>

      <Input
        label="address"
        type="search"
        defaultValue={defaultValue?.address ?? ""}
        {...register("address", {
          maxLength: {
            value: 100,
            message: "Address must be at most 100 characters",
          },
        })}
      >
        Address
      </Input>
      <div>
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
