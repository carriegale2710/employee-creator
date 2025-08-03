import type { Employee } from "../../services/employees";
import type { ReactNode } from "react";
import avatarDefault from "../../assets/avatar-default-svgrepo-com.svg";
import { NavLink } from "react-router-dom";
import Button from "../Button/Button";

import type { ReactNode } from "react";

export interface CardProps {
  employee: Employee;
  buttons?: ReactNode[];
}

const Card = ({ employee, buttons }: CardProps) => {
  return (
    <section
      className="flex flex-row items-start justify-between flex-wrap bg-[#e8e7e7] p-4 m-2 rounded"
      data-testid="card"
      id="card"
    >
      <div>
        <div>
          <img
            src={avatarDefault}
            alt={`profile picture of ${employee.firstName} ${employee.lastName}`}
            className="w-16 h-16 rounded-full mb-4"
          />
        </div>
        <label className="text-gray-600 hidden">Employee Name:</label>
        <p id="employee-name" className="font-bold text-lg mb-4">
          {employee.firstName} {employee.lastName}
        </p>

        <span className="mb-4 flex flex-wrap gap-2 align-middle">
          <label className="text-gray-600">Employee ID:</label>
          <p className="text-gray-600 ">{employee.id}</p>
        </span>

        <label className="text-gray-600">Email:</label>
        <p className="text-gray-600">{employee.email}</p>

        <label className="text-gray-600">Phone:</label>
        <p className="text-gray-600">{employee.phone || ""}</p>

        <label className="text-gray-600">Address:</label>
        <p className="text-gray-600">{employee.address || ""}</p>

        <label className="text-gray-600">Status:</label>
        <p className="text-gray-600">
          {employee.status || "No status available"}
        </p>

        <label className="text-gray-600">Contract Count:</label>
        <p className="text-gray-600">{employee.contractCount || 0}</p>
      </div>

      <div className="flex flex-row gap-2 mt-4 text-sm">
        {buttons?.map((button, idx) => (
          <span key={idx} className=" flex justify-end">
            {button}
          </span>
        ))}
      </div>
    </section>

  );
};

export default Card;
