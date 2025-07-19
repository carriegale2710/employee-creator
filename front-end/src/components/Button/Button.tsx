import type { ButtonHTMLAttributes, DetailedHTMLProps } from "react";

interface ButtonProps
  extends DetailedHTMLProps<
    ButtonHTMLAttributes<HTMLButtonElement>,
    HTMLButtonElement
  > {
  children: React.ReactNode;
  altText?: string;
}

const Button: React.FC<ButtonProps> = ({
  type = "button",
  altText,
  children,
}) => {
  return (
    <>
      <button type={type} aria-label={altText}>
        {children}
      </button>
    </>
  );
};

export default Button;
