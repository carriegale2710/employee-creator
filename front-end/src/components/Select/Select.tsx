import React from "react";

export interface SelectInputProps
  extends React.SelectHTMLAttributes<HTMLSelectElement> {
  label: string;
  title?: string;
  prefilledValue?: string;
  options: string[];
  children: React.ReactNode;
}

const Select = React.forwardRef<HTMLSelectElement, SelectInputProps>(
  ({ label, title, prefilledValue = "", options, children, ...rest }, ref) => (
    <>
      <br />

      <label htmlFor={label}>{children}</label>
      <select
        name={label}
        id={label}
        defaultValue={prefilledValue}
        ref={ref}
        {...rest}
      >
        <option value="" disabled>
          Select {title || ""}
        </option>
        {options.map((option) => (
          <option key={option} value={option}>
            {option}
          </option>
        ))}
      </select>
    </>
  )
);

export default Select;
