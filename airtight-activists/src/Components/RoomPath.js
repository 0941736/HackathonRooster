import React, { Component } from "react";
import path1 from "../images/from_H.1.306_to_H1.318.png";
import path2 from "../images/from_H.1.318_to_H.1.119.png";
// import

export default class RoomPath extends Component {
  render() {
    return (
      <div>
        <h2>Lokalen vinder</h2>
        <h3>Hoe kom je van H.1.318 naar H.1.306?</h3>
        <p>
          In onderstaande afbeelding is aangegeven hoe je dit het beste kunt
          doen.
        </p>
        <div className="Background">
          <img
            src={path1}
            className="Floor-image"
            alt="from h1.306 to h1.318"
          />
        </div>
        <h3>Hoe kom je het beste van H.1.318 naar de dichtsbijzijnde lift</h3>
        <p>
          In onderstaande afbeelding is aangegeven hoe je dit het beste kunt
          doen.
        </p>
        <div className="Background">
          <img
            src={path2}
            className="Floor-image"
            alt="from h1.318 to h1.306"
          />
        </div>
      </div>
    );
  }
}
