import { useState, useEffect } from "react";
import EmployeeForm from "../containers/EmployeeForm/EmployeeForm";
import { NavLink, useParams } from "react-router-dom";
import { getEmployeeById, type Employee } from "../services/employees";
import Header from "../components/Header/Header";

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
      <Header
        heading="Edit Employee"
        subheading="Fill out the form below to edit the employee details."
      />
      <main>
        <NavLink to={`/employees/${id}`}>Go back to Profile</NavLink>
        {employeeData ? (
          <EmployeeForm defaultValue={employeeData} />
        ) : (
          <p>Loading employee data...</p>
        )}
      </main>
    </>
  );
};

export default EditEmployee;
