import { useLocation } from "react-router";

const NotFoundPage: React.FC = () => {
  const location = useLocation();
  return (
    <>
      <header>
        <h1>404 Not Found</h1>
      </header>
      <main>
        <p>Sorry, the page at {location.pathname} doesn't exist.</p>
      </main>
    </>
  );
};

export default NotFoundPage;
