export interface TextInputProps {
  label: string;
  type: string;
  value?: string;
  children: React.ReactNode;
}

const Input = ({ label, type, value, children }: TextInputProps) => {
  return (
    <div>
      <label htmlFor={label}>{children}</label>
      <br />
      <input name={label} type={type} value={value} />
      <br />
    </div>
  );
};

export default Input;
