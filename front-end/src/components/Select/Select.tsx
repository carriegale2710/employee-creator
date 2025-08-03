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
      <div className="mb-4 flex flex-col gap-1 ">
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
        <select
          className="block min-w-0 grow py-1.5 pr-3 pl-1 text-base text-gray-900 placeholder:text-gray-400 focus:outline-none sm:text-sm/6"
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
      </div>

    );
  }
);

export default Select;
