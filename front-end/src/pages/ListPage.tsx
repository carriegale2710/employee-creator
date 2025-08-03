import Button from "../components/Button/Button";
import List from "../components/List/List";
import { NavLink } from "react-router-dom";
import { useEmployeeList } from "../hooks/useEmployeeList";
import Header from "../components/Header/Header";

function ListPage() {
  const { employees, loading } = useEmployeeList();

  return (
    <>
      <Header
        heading="Employee List"
        subheading="View and manage all employees."
      />
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
