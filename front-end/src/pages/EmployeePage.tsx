import { useState, useEffect } from "react";
import Button from "../components/Button/Button";
import { NavLink, useNavigate, useParams } from "react-router-dom";
import {
  deleteEmployee,
  getEmployeeById,
  type Employee,
} from "../services/employees";
import Card from "../components/Card/Card";
import DeleteButton from "../containers/DeleteButton/DeleteButton";
import ContractCard from "../components/ContractCard/ContractCard";

//needs to preload data from selected employee from Card
// you come here on Edit Button

const EmployeePage = () => {
  const { id } = useParams(); //get the employee ID from the URL parameters
  const [employee, setEmployee] = useState<Employee | null>(null);
  const navigate = useNavigate(); //nav back to list page after action

  const [error, setError] = useState<string | null>(null);
  const [isLoading, setIsLoading] = useState<boolean>(true);

  useEffect(() => {
    // Fetch employee data when component mounts or id changes
    setIsLoading(true);
    console.log("Fetching employee data for ID:", id);
    getEmployeeById(Number(id))
      .then(setEmployee)
      .catch((e) => {
        setError(`Could not fetch employee data for ID ${id}: ${e.message}`);
      })
      .finally(() => setIsLoading(false));
  }, [id, error]);

  const onDelete = (employee: Employee) => {
    console.log("Deleting", employee);
    const confirmation = window.confirm(
      "Are you sure? This action cannot be undone."
    );
    if (!confirmation) return;
    deleteEmployee(employee.id)
      .then(() => {
        navigate("/employees");
      })
      .catch((error) => {
        setError(`Failed to delete: ${error.message}`);
      });
  };

  return (
    <>
      <header>
        <h1>
          {employee?.firstName} {employee?.lastName}
        </h1>
        <p>View details below.</p>
      </header>
      <main>
        <NavLink to="/employees">Go back</NavLink>
        {employee ? (
          <Card employee={employee} />
        ) : (
          <p>{isLoading ? "Loading employee data..." : error}</p>
        )}

        <NavLink to={`/employees/${employee != null && employee.id}/edit`}>
          <Button>Edit Employee</Button>
        </NavLink>

        <NavLink
          to={`/employees/${employee != null && employee.id}/contracts/new`}
        >
          <Button>Add Contract</Button>
        </NavLink>

        <NavLink to={`/employees/${employee != null && employee.id}/contracts`}>
          <Button>View Contracts</Button>
        </NavLink>

        <DeleteButton
          onDelete={() => {
            if (employee) onDelete(employee);
          }}
        />
      </main>
    </>
  );
};

export default EmployeePage;
