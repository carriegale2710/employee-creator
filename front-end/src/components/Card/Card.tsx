import type { Employee } from "../../services/employees";
import "./Card.scss";

import type { ReactNode } from "react";

export interface CardProps {
  employee: Employee;
  buttons?: ReactNode[];
}

const Card = ({ employee, buttons }: CardProps) => {
  return (
    <div className="card" data-testid="card" id="card">
      <div>
        <p id="employee-name" style={{ fontWeight: "bold", fontSize: "1.2em" }}>
          {employee.firstName} {employee.lastName}
        </p>
        <p>Employee ID: {employee.id}</p>
        <p id="employee-email">Email: {employee.email} </p>
        <p id="employee-phone">Phone: {employee.phone || ""}</p>
        <p id="employee-address">Address: {employee.address || ""}</p>
      </div>
      <span className="buttons">
        {buttons?.map((button, idx) => (
          <span key={idx}>{button}</span>
        ))}
      </span>
    </div>
  );
};

export default Card;
