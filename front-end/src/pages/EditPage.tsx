import Form from "../containers/Form/Form";
import { employee1 } from "../services/mockEmployees";

//needs to preload data from selected employee from Card
// you come here on Edit Button

const EditPage = () => {
  return (
    <>
      <header>
        <h1>Edit Employee</h1>
        <p>Fill out the form below to edit the employee details.</p>
      </header>
      <main>
        <Form prefilled={employee1} />
      </main>
    </>
  );
};

export default EditPage;
