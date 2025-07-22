import { NavLink } from "react-router-dom";
import Button from "../components/Button/Button";

const Home = () => {
  return (
    <>
      <h1>Home</h1>
      <NavLink to="/employees">View Employees</NavLink>
      <NavLink to="/employees/new">
        <Button>Register an Employee</Button>
      </NavLink>
    </>
  );
};

export default Home;
