import Form from "../containers/EmployeeForm/EmployeeForm";
import { employee1 } from "../services/mockEmployees";

//needs to preload data from selected employee from Card
// you come here on Edit Button

const EditPage = () => {
  return (
    <>
      <h1>Edit Employee</h1>
      <Form prefilled={employee1} />
    </>
  );
};

export default EditPage;
