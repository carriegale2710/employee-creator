import ContractForm from "../containers/ContractForm/ContractForm";
import { contract1 } from "../services/mockContracts";

const CreateContract = () => {
  return (
    <>
      <h1>New Contract</h1>
      <ContractForm prevContract={contract1} />
    </>
  );
};

export default CreateContract;
