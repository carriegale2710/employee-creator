interface HeaderProps {
  heading: string;
  subheading?: string;
  children?: React.ReactNode;
}

const Header = ({ heading, subheading, children }: HeaderProps) => {
  return (
    <header className="bg-gray-800 text-white p-4 shadow-md mb-6 text-center flex flex-row flex-wrap align-middle justify-between items-center px-12 py-10">
      <h1 className="text-white text-2xl font-bold ">{heading}</h1>
      <p className="text-sm">{subheading}</p>
      {children}
    </header>
  );
};

export default Header;
