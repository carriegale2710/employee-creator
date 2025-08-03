import React from "react";

interface HeaderProps {
  heading: string;
  subheading?: string;
}

const Header = ({ heading, subheading }: HeaderProps) => {
  return (
    <header className="bg-gray-800 text-white p-4 shadow-md mb-6 text-center ">
      <h1 className="text-white text-2xl font-bold ">{heading}</h1>
      <p className="text-sm">{subheading}</p>
    </header>
  );
};

export default Header;
