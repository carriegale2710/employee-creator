import type { Contract } from "../../services/contracts";

export interface ContractProps {
  contract: Contract | null;
}

const ContractCard = ({ contract }: ContractProps) => {
  return (
    <div
      style={{
        marginTop: "20px",
        border: "1px solid #ccc",
        padding: "10px",
        borderRadius: "5px",
        backgroundColor: "#f9f9f9",
        boxShadow: "0 2px 4px rgba(0, 0, 0, 0.1)",
      }}
    >
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
