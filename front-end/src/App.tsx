import { BrowserRouter, Route, Routes } from "react-router";
import NavBar from "./components/NavBar/NavBar";
import Home from "./pages/Home";
import ListPage from "./pages/ListPage";
import CreateEmployee from "./pages/CreateEmployee";
import EditEmployee from "./pages/EditEmployee";
import NotFoundPage from "./pages/NotFoundPage";
import "./App.scss";
import CreateContract from "./pages/CreateContract";
import EmployeePage from "./pages/EmployeePage";
import ContractsPage from "./pages/ContractsPage";

function App() {
  return (
    <>
      <BrowserRouter>
        <NavBar />
        <Routes>
          <Route path="/" element={<Home />} />
          <Route path="/*" element={<NotFoundPage />} />
          <Route path="/employees" element={<ListPage />} />
          <Route path="/employees/new" element={<CreateEmployee />} />
          <Route path="/employees/:id" element={<EmployeePage />} />
          <Route path="/employees/:id/edit" element={<EditEmployee />} />
          <Route path="/employees/:id/contracts" element={<ContractsPage />} />
          <Route
            path="/employees/:id/contracts/new"
            element={<CreateContract />}
          />
        </Routes>
      </BrowserRouter>
      <footer>
        <p>&copy; 2025 Company Name Inc.</p>
      </footer>
    </>
  );
}

export default App;
