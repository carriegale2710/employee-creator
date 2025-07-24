import type { Employee } from "../../services/employees";
import { NavLink } from "react-router-dom";
import Button from "../Button/Button";
import "./Card.scss";

export interface CardProps {
  employee: Employee;
}

const Card = ({ employee }: CardProps) => {
  return (
    <div className="card" data-testid="card" id="card">
      <div>
        <p id="employee-name" style={{ fontWeight: "bold", fontSize: "1.2em" }}>
          {employee.firstName} {employee.lastName}
        </p>
        <p id="employee-email">{employee.email} </p>
        <p id="employee-phone">{employee.phone || ""}</p>
        <p id="employee-address">{employee.address || ""}</p>
      </div>
      <span className="buttons">
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
