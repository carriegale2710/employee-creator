export interface ContractInputProps {
  label: string;
  type: string;
  value?: string;
  children: React.ReactNode;
}

const ContractInput = ({
  label,
  type,
  value,
  children,
}: ContractInputProps) => {
  return (
    <div>
      <label htmlFor={label}>{children}</label>
      <br />
      <input name={label} type={type} value={value} />
      <br />
    </div>
  );
};

export default ContractInput;
