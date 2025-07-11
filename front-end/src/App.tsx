import { useEffect, useState } from "react";
import "./App.scss";
import Button from "./components/Button/Button";
import List from "./components/List/List";
import { getAllEmployees, type Employee } from "./services/employees";

function App() {
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
      <p>testing</p>
      <div className="intro">
        <p>Click on "Edit" to see more about each employee.</p>
        <Button>Add Employee</Button>
      </div>
      <List employees={employees} />
    </>
  );
}

export default App;
