import { NavLink } from "react-router-dom";
import type { Employee } from "../../services/employees";
import Card from "../Card/Card";
import Button from "../Button/Button";

interface ListProps {
  employees: Employee[];
}
const List = ({ employees }: ListProps) => {
  return (
    <>
      {employees &&
        employees.map((employee: Employee) => {
          return (
            <Card
              employee={employee}
              key={employee.id}
              buttons={[
                <NavLink to={`/employees/${employee.id}`}>
                  <Button>View</Button>
                </NavLink>,
              ]}
            />
          );
        })}
    </>
  );
};

export default List;
