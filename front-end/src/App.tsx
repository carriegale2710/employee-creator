import "./App.scss";
import CreatePage from "./pages/CreatePage";
import EditPage from "./pages/EditPage";
import ListPage from "./pages/ListPage";

function App() {
  return (
    <>
      <ListPage />
      <EditPage />
      <CreatePage />
    </>
  );
}

export default App;
