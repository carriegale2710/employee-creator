import { NavLink, useParams } from "react-router-dom";
import Button from "../components/Button/Button";
import ContractForm from "../containers/ContractForm/ContractForm";
import { getEmployeeById, type Employee } from "../services/employees";
import { useEffect, useState } from "react";
import Header from "../components/Header/Header";

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
      <Header
        heading="New Contract"
        subheading="Fill out the form below to create a new contract for the employee."
      />
      <main>
        {loading ? <p>Loading...</p> : <ContractForm employee={employee} />}

        <p className="text-blue-500 italic">
          <strong>Warning:</strong> Do not navigate away from this page without
          submitting your changes. Your changes will be lost.
        </p>
      </main>
    </>
  );
};

export default CreateContract;
