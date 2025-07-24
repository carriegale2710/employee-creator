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
      <span className="row">
        <div className="data">
          <p>
            {employee.firstName} {employee.lastName}
          </p>
          <p>{employee.email} </p>
        </div>
        <span className="buttons">
          <NavLink to={`/employees/${employee.id}/edit`}>
            <Button>Edit</Button>
          </NavLink>
          <Button>Delete</Button>
        </span>
      </span>
    </div>
  );
};

export default Card;
