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
    <div className="mb-4 flex flex-col gap-1  sm:col-span-3">
      <label
        htmlFor={label}
        className="flex justify-between block text-sm/6 font-medium text-gray-900"
      >
        {children}
        {errors && (
          <span className="text-red-500 italic font-normal">
            {errors.message}
          </span>
        )}
      </label>

      <input
        name={label}
        id={label}
        type={type}
        ref={ref}
        {...rest}
        className="block min-w-0 grow py-1.5 pr-3 pl-1 text-base text-gray-900 placeholder:text-gray-400 focus:outline-none sm:text-sm/6 "
      />
    </div>
  )
);

export default Input;
