import React from "react";
import type { FieldError } from "react-hook-form";

export interface TextInputProps
  extends React.InputHTMLAttributes<HTMLInputElement> {
  errors?: FieldError | undefined;
  label: string;
  type: string;
  children: React.ReactNode;
}

const Input = React.forwardRef<HTMLInputElement, TextInputProps>(
  ({ errors, label, type, children, ...rest }, ref) => (
    <div>
      <label
        htmlFor={label}
        style={{ display: "flex", justifyContent: "space-between" }}
      >
        {children}
        {errors && (
          <span
            style={{ color: "red", fontStyle: "italic", fontWeight: "normal" }}
          >
            {errors.message}
          </span>
        )}
      </label>

      <input name={label} id={label} type={type} ref={ref} {...rest} />
    </div>
  )
);

export default Input;
