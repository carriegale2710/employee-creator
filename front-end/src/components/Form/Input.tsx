export interface TextInputProps {
  label: string;
  type: string;
  placeholder?: string;
  children: React.ReactNode;
}

const Input = ({ label, type, placeholder, children }: TextInputProps) => {
  return (
    <div>
      <label htmlFor={label}>{children}</label>
      <br />
      <input name={label} type={type} placeholder={placeholder} />
      <br />
    </div>
  );
};

export default Input;
