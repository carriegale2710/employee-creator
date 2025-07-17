import { Link } from "react-router-dom";

const NavBar = () => (
  <nav>
    <Link to="/">Home</Link>
    <Link to="/employees">Employees</Link>
    <Link to="/employees/new">Register</Link>
  </nav>
);

export default NavBar;
