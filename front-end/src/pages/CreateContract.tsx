import ContractForm from "../containers/ContractForm/ContractForm";
import { contract1 } from "../services/mockContracts";

const CreateContract = () => {
  return (
    <>
      <header>
        <h1>New Contract</h1>
        <p>
          Fill out the form below to create a new contract for the employee.
        </p>
      </header>
      <main>
        <ContractForm prevContract={contract1} />
      </main>
    </>
  );
};

export default CreateContract;
