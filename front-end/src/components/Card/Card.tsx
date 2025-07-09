import Button from "../Button/Button";
import "./Card.scss";

const Card = () => {
  return (
    <div className="card">
      <span className="row">
        <div className="data">
          <h2>Card</h2>
          <p>First Name</p>
          <p>Last Name</p>
          <p>Email: </p>
        </div>
        <span className="buttons">
          <Button>Edit</Button>
          <Button>Delete</Button>
        </span>
      </span>
    </div>
  );
};

export default Card;
