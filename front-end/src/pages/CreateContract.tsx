import ContractForm from "../components/ContractForm/ContractForm";
import { contract1 } from "../services/mockContracts";

const CreateContract = () => {
  return (
    <>
      <h1>New Contract</h1>

      <ContractForm prefilled={contract1} />
    </>
  );
};

export default CreateContract;
