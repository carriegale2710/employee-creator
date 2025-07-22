import ContractForm from "../components/ContractForm/ContractForm";
import { contract1 } from "../services/mockContracts";

const DuplicateContract = () => {
  return (
    <>
      <h1>Duplicate Contract</h1>
      <ContractForm prefilled={contract1} />
    </>
  );
};

export default DuplicateContract;
