import { useEffect, useState } from "react";
import { getAllEmployees, type Employee } from "../services/employees";
import Button from "../components/Button/Button";
import List from "../components/List/List";
import { NavLink } from "react-router-dom";

function ListPage() {
  const [employees, setEmployees] = useState<Employee[]>([]);

  useEffect(() => {
    getAllEmployees()
      .then((response) => {
        console.log(response);
        setEmployees(response);
      })
      .catch(console.warn);
  }, []);

  return (
    <>
      <header>
        <h1>Employee List</h1>
        <p>View and manage all employees.</p>
      </header>
      <main>
        <NavLink to="/employees/new">
          <Button>Add Employee</Button>
        </NavLink>
        <List employees={employees} />
      </main>
    </>
  );
}

export default ListPage;
