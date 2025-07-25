import { NavLink } from "react-router-dom";
import EmployeeForm from "../containers/EmployeeForm/EmployeeForm";
import Button from "../components/Button/Button";

const CreatePage = () => {
  return (
    <>
      <header>
        <h1>Register Employee</h1>
        <p>Fill out the form below to register a new employee.</p>
      </header>
      <main>
        <EmployeeForm />

        <NavLink to="/employees">
          <Button
            style={{ backgroundColor: "lightgray", color: "black" }}
            type="button"
            onClick={() => console.log("Cancel button clicked")}
          >
            Cancel
          </Button>
        </NavLink>
      </main>
    </>
  );
};

export default CreatePage;
