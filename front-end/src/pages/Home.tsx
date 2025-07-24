import { NavLink } from "react-router-dom";
import Button from "../components/Button/Button";

const Home = () => {
  return (
    <>
      <h1>Home</h1>
      <NavLink to="/employees">View Employees</NavLink>
      <Button>Register an Employee</Button>
    </>
  );
};

export default Home;
