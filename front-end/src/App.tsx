import { BrowserRouter, Route, Routes } from "react-router";
import Home from "./pages/Home";
import ListPage from "./pages/ListPage";
import CreatePage from "./pages/CreatePage";
import EditPage from "./pages/EditPage";
import NotFoundPage from "./pages/NotFoundPage";
import "./App.scss";

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/employees" element={<ListPage />} />
        <Route path="/employees/:id/edit" element={<EditPage />} />
        <Route path="/employees/new" element={<CreatePage />} />
        <Route path="/*" element={<NotFoundPage />} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;
