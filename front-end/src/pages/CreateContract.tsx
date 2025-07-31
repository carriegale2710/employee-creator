import { NavLink, useParams } from "react-router-dom";
import Button from "../components/Button/Button";
import ContractForm from "../containers/ContractForm/ContractForm";
import { getEmployeeById, type Employee } from "../services/employees";
import { useEffect, useState } from "react";

const CreateContract = () => {
  const { id } = useParams();
  const [employee, setEmployee] = useState<Employee | null>(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    getEmployeeById(id ? Number(id) : 0)
      .then((employee) => {
        if (employee) {
          setEmployee(employee);
        }
      })
      .catch((error) => {
        console.error(error);
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
        {loading ? <p>Loading...</p> : <ContractForm employee={employee} />}

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
