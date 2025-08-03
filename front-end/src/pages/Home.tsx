import { NavLink } from "react-router-dom";
import Button from "../components/Button/Button";
import Header from "../components/Header/Header";

const Home = () => {
  return (
    <>
      <Header
        heading="Home"
        subheading="Welcome to the Employee Management System!"
      />
      <main className="text-center">
        <h2>Get Started</h2>
        <p>
          Use the buttons below or navigation links above to navigate the
          system.
        </p>
        <div>
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
