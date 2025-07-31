import { NavLink, useParams } from "react-router-dom";
import Button from "../components/Button/Button";
import ContractForm from "../containers/ContractForm/ContractForm";
import { contract1 } from "../services/mockContracts";
import { getEmployeeById, type Employee } from "../services/employees";

import { useEffect, useState } from "react";
import type { Contract } from "../services/contracts";

const CreateContract = () => {
  const { id } = useParams(); // This will extract the employee ID from the URL if needed
  const [employee, setEmployee] = useState<Employee | null>(null);
  const [loading, setLoading] = useState(true);
  const [prevContract, setPrevContract] = useState<Contract | null>(null);

  useEffect(() => {
    getEmployeeById(id ? Number(id) : 0)
      .then((employee) => {
        if (employee) {
          setEmployee(employee);
          if (employee.contracts && employee.contracts.length > 0) {
            setPrevContract(employee.contracts[0]);
          }
        }
      })
      .catch((error) => {
        console.error("Error fetching employee data:", error);
      })
      .finally(() => setLoading(false));
  }, [id]);

  return (
    <>
      <header>
        <h1>New Contract</h1>
        <p>
          Fill out the form below to create a new contract for the employee.
        </p>
      </header>
      <main>
        {loading ? (
          <p>Loading...</p>
        ) : (
          <ContractForm employee={employee} prevContract={prevContract} />
        )}

        <NavLink to="/employees">
          <Button
            style={{ backgroundColor: "lightgray", color: "black" }}
            type="button"
            onClick={() => console.log("Cancel button clicked")}
          >
            Cancel
          </Button>
          <p>
            <strong>Warning:</strong> Do not navigate away from this page
            without submitting your changes. Your changes will be lost.
          </p>
        </NavLink>
      </main>
    </>
  );
};

export default CreateContract;
