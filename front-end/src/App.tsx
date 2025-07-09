import "./App.scss";
import Button from "./components/Button/Button";
import List from "./components/List/List";

function App() {
  return (
    <>
      <h1>Employee Creator App</h1>
      <div className="intro">
        <p>Click on "Edit" to see more about each employee.</p>
        <Button>Add Employee</Button>
      </div>
      <List />
    </>
  );
}

export default App;
