import { useLocation } from "react-router";
import Header from "../components/Header/Header";

const NotFoundPage: React.FC = () => {
  const location = useLocation();
  return (
    <>
      <Header
        heading="404 Page Not Found"
        subheading={`Sorry, check the URL or go back to the home page.`}
      />
      <main>
        <p>Sorry, the page at {location.pathname} doesn't exist.</p>
      </main>
    </>
  );
};

export default NotFoundPage;
