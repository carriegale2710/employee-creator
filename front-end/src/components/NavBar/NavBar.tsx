import { Link } from "react-router-dom";

const NavBar = () => (
  <nav className="bg-white flex gap-4 items-center text-sm p-4 border-b border-[#ccc] shadow-md sticky top-0 z-50 justify-center w-full m-0  px-8">
    <Link to="/">Home</Link>
    <Link to="/employees">Employees</Link>
    <Link to="/employees/new">Register</Link>
  </nav>
);

export default NavBar;
