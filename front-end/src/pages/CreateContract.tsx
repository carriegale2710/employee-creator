import { NavLink } from "react-router-dom";
import ContractForm from "../components/ContractForm/ContractForm";

const CreateContract = () => {
  return (
    <>
      <h1>New Contract</h1>
      <h2>Enter Contract Details</h2>
      <NavLink to={`/employees/new/contract`}>
        Use previous contract as template
      </NavLink>
      <ContractForm />
    </>
  );
};

export default CreateContract;
