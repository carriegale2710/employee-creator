import React from "react";
import type { FieldError } from "react-hook-form";

export interface SelectInputProps
  extends React.SelectHTMLAttributes<HTMLSelectElement> {
  errors?: FieldError | undefined;
  label: string;
  title?: string;
  defaultValue?: string;
  options: string[];
  children: React.ReactNode;
}

const Select = React.forwardRef<HTMLSelectElement, SelectInputProps>(
  (
    { errors, label, title, defaultValue = "", options, children, ...rest },
    ref
  ) => {
    return (
      <>
        <label htmlFor={label}>{children}</label>
        {errors && <span style={{ color: "red" }}>{errors.message}</span>}
        <select
          name={label}
          id={label}
          defaultValue={
            options.find(
              (option) => option === defaultValue.split("_").join(" ")
            ) || ""
          }
          ref={ref}
          {...rest}
        >
          <option value="" disabled>
            Select {title || ""}
          </option>

          {options.map((option, idx) => (
            <option key={`${option}-${idx}`} value={option}>
              {option}
            </option>
          ))}
        </select>
        <br />
      </>
    );
  }
);

export default Select;
