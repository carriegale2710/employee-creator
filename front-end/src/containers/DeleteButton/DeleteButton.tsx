import { useState } from "react";
import Button from "../../components/Button/Button";

export interface DeleteButtonProps {
  onDelete: () => void; // Function to call when delete button is clicked
}

const DeleteButton = ({ onDelete }: DeleteButtonProps) => {
  const [warningVisible, setWarningVisible] = useState(false);

  return (
    <>
      <Button
        variant="danger"
        type="button"
        onClick={onDelete}
        onMouseOver={() => {
          setWarningVisible(true);
        }}
        onMouseOut={() => {
          setWarningVisible(false);
        }}
      >
        Delete Employee
      </Button>

      {/* Warning message that appears on hover */}
      {warningVisible && (
        <span id="delete-warning" className="text-red-500">
          Are you sure? This action cannot be undone.
        </span>
      )}
    </>
  );
};

export default DeleteButton;
