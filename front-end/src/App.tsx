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
      <main>
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
      </main>
      <footer>
        <p>&copy; 2025 Carrie Gale. All rights reserved.</p>
      </footer>
    </BrowserRouter>
  );
}

export default App;
