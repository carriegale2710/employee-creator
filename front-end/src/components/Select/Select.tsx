export interface SelectInputProps {
  label: string;
  title?: string;
  prefilledValue?: string;
  type: string;
  options?: string[];
  children: React.ReactNode;
}

const Select = ({
  label,
  title,
  prefilledValue,
  type,
  options,
  children,
}: SelectInputProps) => {
  return (
    <div>
      <label htmlFor={label}>{children}</label>
      <br />
      <select
        typeof={type}
        name={label}
        id={label}
        value={prefilledValue ? prefilledValue : ""}
      >
        <option value="" disabled selected>
          Select {title || "Option"}
        </option>
        {options?.map((option) => (
          <option key={option} value={option}>
            {option}
          </option>
        ))}
      </select>
      <br />
    </div>
  );
};

export default Select;
