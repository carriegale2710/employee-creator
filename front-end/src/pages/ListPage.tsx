import { useEffect, useState } from "react";
import { getAllEmployees, type Employee } from "../services/employees";
import Button from "../components/Button/Button";
import List from "../components/List/List";

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
      <h1>Employee Creator App</h1>
      <div className="intro">
        <p>Click on "Edit" to see more about each employee.</p>
        <Button>Add Employee</Button>
      </div>
      <List employees={employees} />
    </>
  );
}

export default ListPage;
