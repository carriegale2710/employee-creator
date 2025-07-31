import Button from "../components/Button/Button";
import List from "../components/List/List";
import { NavLink } from "react-router-dom";
import { useEmployeeList } from "../hooks/useEmployeeList";

function ListPage() {
  const { employees, loading } = useEmployeeList();

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
        {loading ? <p>Loading...</p> : <List employees={employees} />}
      </main>
    </>
  );
}

export default ListPage;
