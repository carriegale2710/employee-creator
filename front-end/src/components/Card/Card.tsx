import type { Employee } from "../../services/employees";
import { NavLink } from "react-router-dom";
import Button from "../Button/Button";
import "./Card.scss";

export interface CardProps {
  employee: Employee;
}

const Card = ({ employee }: CardProps) => {
  return (
    <div className="card">
      <div>
        <p>
          {employee.firstName} {employee.lastName}
        </p>
        <p>{employee.email} </p>
      </div>
      <span className="row">
        <NavLink to={`/employees/${employee.id}/edit`}>
          <Button>Edit</Button>
        </NavLink>
        <Button>Delete</Button>
        <br />
        <NavLink to={`/employees/new/contract`}>
          <Button>New Contract</Button>
        </NavLink>
      </span>
    </div>
  );
};

export default Card;
