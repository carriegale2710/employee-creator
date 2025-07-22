import { BrowserRouter, Route, Routes } from "react-router";
import NavBar from "./components/NavBar/NavBar";
import Home from "./pages/Home";
import ListPage from "./pages/ListPage";
import CreatePage from "./pages/CreatePage";
import EditPage from "./pages/EditPage";
import NotFoundPage from "./pages/NotFoundPage";
import "./App.scss";
import CreateContract from "./pages/CreateContract";

function App() {
  return (
    <BrowserRouter>
      <NavBar />
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/employees" element={<ListPage />} />
        <Route path="/employees/new" element={<CreatePage />} />
        <Route path="/employees/:id/edit" element={<EditPage />} />
        <Route path="/employees/new/contract" element={<CreateContract />} />
        <Route
          path="/employees/:id/new/contract"
          element={<CreateContract />}
        />
        <Route path="/*" element={<NotFoundPage />} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;
