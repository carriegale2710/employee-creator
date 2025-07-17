import type { Employee } from "../../services/employees";
import Card from "../Card/Card";

interface ListProps {
  employees: Employee[];
}
const List = ({ employees }: ListProps) => {
  return (
    <>
      {employees &&
        employees.map((employee: Employee) => {
          return <Card employee={employee} key={employee.id} />;
        })}
    </>
  );
};

export default List;
