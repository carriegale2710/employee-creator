import type { Contract } from "../../services/contracts";

export interface ContractProps {
  contract: Contract | null;
}

const ContractCard = ({ contract }: ContractProps) => {
  return (
    <div className="mt-5 border border-gray-300 p-4 rounded bg-gray-50 shadow">
      {contract ? (
        <>
          <p>Contract ID: {contract.id}</p>
          <p>Contract Type: {contract.contractType}</p>
          <p>Department: {contract.department}</p>
          <p>Salary Amount: ${contract.salaryAmount.toFixed(2)}</p>
          <p>Hours Per Week: {contract.hoursPerWeek}</p>
          <p>Start Date: {contract?.startDate}</p>
          <p>End Date: {contract?.endDate ? contract?.endDate : "Ongoing"}</p>
        </>
      ) : (
        <p>No contract details available.</p>
      )}
    </div>
  );
};

export default ContractCard;
