import { useState } from "react";
import Button from "../components/Button/Button";
import EmployeeForm from "../containers/EmployeeForm/EmployeeForm";
import { employee1 } from "../services/mockEmployees";
import { NavLink, useParams } from "react-router-dom";
import { deleteEmployee } from "../services/employees";

//needs to preload data from selected employee from Card
// you come here on Edit Button

const onDelete = (id: number) => {
  // Logic to handle delete action
  console.log("Delete Employee button clicked");
  deleteEmployee(id)
    .then(() => {
      console.log("Employee deleted successfully");
      // Redirect or update state to reflect deletion
    })
    .catch((error) => {
      console.error("Error deleting employee:", error);
      // Handle error, e.g., show a notification
    });
};

const EditPage = () => {
  const [deleteWarningVisible, setDeleteWarningVisible] = useState(false);
  const { id } = useParams();
  if (!id) {
    throw new Error("Employee ID is required for editing");
  }

  return (
    <>
      <header>
        <h1>Edit Employee</h1>
        <p>Fill out the form below to edit the employee details.</p>
      </header>
      <main>
        <NavLink to="/employees">Go back to Employees</NavLink>
        <EmployeeForm prefilled={employee1} />
        <br />
        <p style={{ color: "red", fontWeight: "bold" }}>Danger Zone</p>

        <Button
          style={{ backgroundColor: "red" }}
          type="button"
          onClick={() => onDelete(Number(id))}
          onMouseOver={() => {
            setDeleteWarningVisible(true);
          }}
          onMouseOut={() => {
            setDeleteWarningVisible(false);
          }}
        >
          Delete Employee
        </Button>

        <span
          id="delete-warning"
          style={{
            color: "red",
            display: deleteWarningVisible ? "block" : "none",
          }}
        >
          Are you sure? This will delete the employee and all associated
          contracts.
        </span>
      </main>
    </>
  );
};

export default EditPage;
