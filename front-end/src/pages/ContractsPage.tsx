import { useEffect, useState } from "react";
import ContractCard from "../components/ContractCard/ContractCard";
import { NavLink, useParams } from "react-router-dom";
import { getContractsByEmployeeId, type Contract } from "../services/contracts";
import Header from "../components/Header/Header";

const ContractsPage = () => {
  const { id } = useParams(); //get the employee ID from the URL parameters
  const [contracts, setContract] = useState<Contract[] | null>(null);

  const [error, setError] = useState<string | null>(null);
  const [isLoading, setIsLoading] = useState<boolean>(true);

  useEffect(() => {
    // Fetch contract data when component mounts or id changes
    setIsLoading(true);
    console.log("Fetching contract data for ID:", id);
    getContractsByEmployeeId(Number(id))
      .then((contracts) => {
        if (contracts && contracts.length > 0) {
          setContract(contracts);
        } else {
          setError(`No contract found for employee ID ${id}`);
        }
      })
      .catch((e) => {
        setError(`Could not fetch contract data for ID ${id}: ${e.message}`);
      })
      .finally(() => setIsLoading(false));
  }, [id, error]);

  return (
    <>
      <Header heading="Contract Details" subheading="View details below." />
      <main>
        <NavLink to={`/employees/${id}`}>Go back</NavLink>
        {contracts ? (
          contracts.map((contract) => (
            <ContractCard key={contract.id} contract={contract} />
          ))
        ) : (
          <p>{isLoading ? "Loading contract data..." : error}</p>
        )}
      </main>
    </>
  );
};

export default ContractsPage;
