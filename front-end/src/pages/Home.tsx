import { NavLink } from "react-router-dom";
import Button from "../components/Button/Button";

const Home = () => {
  return (
    <>
      <header>
        <h1>Home</h1>
        <p>Welcome to the Employee Management System!</p>
        <NavLink to="/employees">View all Employees</NavLink>
      </header>
      <main>
        <h2>Get Started</h2>
        <p>Here you can create employee records and contracts.</p>
        <p>Click on a button below to get started.</p>
        <div style={{ marginTop: "20px" }}>
          <NavLink to="/employees/new">
            <Button>Register an Employee</Button>
          </NavLink>
          <NavLink to="/employees/new/contract">
            <Button>Create a New Contract</Button>
          </NavLink>
        </div>
      </main>
    </>
  );
};

export default Home;
