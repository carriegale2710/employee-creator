import { useState, useEffect } from "react";
import EmployeeForm from "../containers/EmployeeForm/EmployeeForm";
import { NavLink, useParams } from "react-router-dom";
import { getEmployeeById, type Employee } from "../services/employees";

const EditEmployee = () => {
  //needs to preload data from selected employee from Card
  const { id } = useParams(); //get the employee ID from the URL parameters
  const [employeeData, setEmployeeData] = useState<Employee | null>(null);

  useEffect(() => {
    // Fetch employee data when component mounts or id changes

    console.log("Fetching employee data for ID:", id);
    getEmployeeById(Number(id))
      .then(setEmployeeData)
      .catch((error) => {
        console.error("Error fetching employee data:", error);
      });
  }, [id]);

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
      </main>
    </>
  );
};

export default EditEmployee;
