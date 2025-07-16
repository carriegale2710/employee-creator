import type { Employee } from "../../services/employees";
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
          <h2>Card</h2>
          <p>First Name: {employee.firstName} </p>
          <p>Last Name: {employee.lastName} </p>
          <p>Email: {employee.email} </p>
        </div>
        <span className="buttons">
          <Button>Edit</Button>
          <Button>Delete</Button>
        </span>
      </span>
    </div>
  );
};

export default Card;
