import React from "react";

export interface TextInputProps
  extends React.InputHTMLAttributes<HTMLInputElement> {
  label: string;
  type: string;
  prefilledValue: string;
  children: React.ReactNode;
}

const Input = React.forwardRef<HTMLInputElement, TextInputProps>(
  ({ label, type, prefilledValue = "", children, ...rest }, ref) => (
    <div>
      <label htmlFor={label}>{children}</label>
      <input
        name={label}
        id={label}
        type={type}
        defaultValue={prefilledValue}
        ref={ref}
        {...rest}
      />
      <br />
    </div>
  )
);

export default Input;
