import { NavLink } from "react-router-dom";
import Button from "../components/Button/Button";

const Home = () => {
  return (
    <>
      <header>
        <h1>Home</h1>
        <p>Welcome to the Employee Management System!</p>
      </header>
      <main>
        <h2>Get Started</h2>
        <p>Here you can manage employee records and contracts.</p>
        <p>Use the navigation links to get started.</p>
        <NavLink to="/employees">View Employees</NavLink>
        <NavLink to="/employees/new">
          <Button>Register an Employee</Button>
        </NavLink>
      </main>
    </>
  );
};

export default Home;
