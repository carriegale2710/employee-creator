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
        <NavLink to="/employees/new" className="flex justify-end ">
          <Button variant="submit" altText="Register a new employee">
            Register Employee
          </Button>
        </NavLink>
        {loading ? <p>Loading...</p> : <List employees={employees} />}
      </main>
    </>
  );
}

export default ListPage;
