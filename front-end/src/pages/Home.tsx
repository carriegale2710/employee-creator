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
        <p>
          {" "}
          Use the buttons below or navigation links above to navigate the
          system.
        </p>

        <div style={{ marginTop: "20px" }}>
          <NavLink to="/employees/new">
            <Button>Register New Employee</Button>
          </NavLink>

          <NavLink to="/employees">
            <Button>View Current Employees</Button>
          </NavLink>
        </div>
      </main>
    </>
  );
};

export default Home;
