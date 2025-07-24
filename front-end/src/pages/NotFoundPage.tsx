import { useLocation } from "react-router";

const NotFoundPage: React.FC = () => {
  const location = useLocation();
  return (
    <>
      <h1>Sorry, the page at {location.pathname} doesn't exist</h1>
    </>
  );
};

export default NotFoundPage;
