import Header from "../components/Header/Header";
import EmployeeForm from "../containers/EmployeeForm/EmployeeForm";

const CreatePage = () => {
  return (
    <>
      <Header
        heading="Register Employee"
        subheading="Fill out the form below to register a new employee."
      />
      <main>
        <EmployeeForm />
        <div className="mt-4 justify-center text-center">
          <p className="mt-1 text-sm/6 text-gray-600">
            <strong>Warning:</strong> Do not navigate away from this page
            without submitting your changes. Your changes will be lost.
          </p>
        </div>
      </main>
    </>
  );
};

export default CreatePage;
