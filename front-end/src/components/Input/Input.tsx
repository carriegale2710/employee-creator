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
    <div className="mb-4">
      <label htmlFor={label} className="flex justify-between">
        {children}
        {errors && (
          <span className="text-red-500 italic font-normal">
            {errors.message}
          </span>
        )}
      </label>

      <input name={label} id={label} type={type} ref={ref} {...rest} />
    </div>
  )
);

export default Input;
