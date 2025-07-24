import EmployeeForm from "../containers/EmployeeForm/EmployeeForm";

const CreatePage = () => {
  return (
    <>
      <header>
        <h1>Register Employee</h1>
        <p>Fill out the form below to register a new employee.</p>
      </header>
      <main>
        <EmployeeForm />
      </main>
    </>
  );
};

export default CreatePage;
