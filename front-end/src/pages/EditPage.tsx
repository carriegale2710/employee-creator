import { useState, useEffect } from "react";
import Button from "../components/Button/Button";
import EmployeeForm from "../containers/EmployeeForm/EmployeeForm";
import { NavLink, useNavigate, useParams } from "react-router-dom";
import {
  deleteEmployee,
  getEmployeeById,
  type Employee,
} from "../services/employees";

//needs to preload data from selected employee from Card
// you come here on Edit Button

const EditPage = () => {
  const { id } = useParams(); //get the employee ID from the URL parameters

  const [deleteWarningVisible, setDeleteWarningVisible] = useState(false);
  const [employeeData, setEmployeeData] = useState<Employee | null>(null);
  const navigate = useNavigate(); //nav back to list page after action

  useEffect(() => {
    // Fetch employee data when component mounts or id changes

    console.log("Fetching employee data for ID:", id);
    getEmployeeById(Number(id))
      .then(setEmployeeData)
      .catch((error) => {
        console.error("Error fetching employee data:", error);
      });

    // Uncomment the following line for debugging only:
    // if (process.env.NODE_ENV === "development") console.log("Employee data fetched:", employeeData);
  }, [id]);

  const onDelete = (id: number) => {
    console.log("Deleting Employee ID:", id);
    console.log("with form data:", employeeData);

    deleteEmployee(id)
      .then(() => {
        console.log("Employee deleted successfully");
        navigate("/employees");
      })
      .catch((error) => {
        console.error("Error deleting employee:", error);
        // Handle error, e.g., show a notification
      });
  };

  return (
    <>
      <header>
        <h1>Edit Employee</h1>
        <p>Fill out the form below to edit the employee details.</p>
      </header>
      <main>
        <NavLink to="/employees">Go back to Employees</NavLink>
        {employeeData ? (
          <EmployeeForm prefilled={employeeData} />
        ) : (
          <p>Loading employee data...</p>
        )}
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
