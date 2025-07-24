import { Link } from "react-router-dom";

const NavBar = () => (
  <nav
    style={{
      display: "flex",
      gap: "1rem",
      alignItems: "center",
      fontSize: "1.2rem",
      padding: "1rem",
      backgroundColor: "#f0f0f0",
      borderBottom: "1px solid #ccc",
      boxShadow: "0 2px 4px rgba(0, 0, 0, 0.1)",
      position: "sticky",
      top: 0,
      zIndex: 1000,
      justifyContent: "space-between",
      width: "100%",
      maxWidth: "1200px",
      margin: "0 auto",
      boxSizing: "border-box",
      paddingLeft: "2rem",
    }}
  >
    <Link to="/">Home</Link>
    <Link to="/employees">Employees</Link>
    <Link to="/employees/new">Register</Link>
  </nav>
);

export default NavBar;
