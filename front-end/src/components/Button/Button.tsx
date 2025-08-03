import type { ButtonHTMLAttributes, DetailedHTMLProps } from "react";

interface ButtonProps
  extends DetailedHTMLProps<
    ButtonHTMLAttributes<HTMLButtonElement>,
    HTMLButtonElement
  > {
  children: React.ReactNode;
  altText?: string;
  type?: "button" | "submit" | "reset";
  variant?: "primary" | "secondary" | "danger" | "link" | "submit";
}

const Button: React.FC<ButtonProps> = ({
  type = "button",
  altText,
  children,
  variant = "primary",
  ...rest
}) => {
  const variants: Record<NonNullable<ButtonProps["variant"]>, string> = {
    primary: "bg-blue-600 hover:bg-blue-700 text-white",
    secondary: "bg-gray-200 hover:bg-gray-300 text-gray-800",
    danger: "bg-red-600 hover:bg-red-700 text-white",
    link: "text-blue-600 hover:underline background-none",
    submit: "bg-green-600 hover:bg-green-700 text-white",
  };
  return (
    <>
      <button
        type={type}
        aria-label={altText}
        className={variants[variant]}
        {...rest}
      >
        {children}
      </button>
    </>
  );
};

export default Button;
