import React, { Component } from "react";
import Verd1 from "./images/IllustratorFiles/107 1e-01_v2-01.png";
// import hrlogo from "./images/school-logo.png";
import "./App.css";

import ClassRooms from "./Components/ClassRooms";
import Header from "./Components/Header";
import Footer from "./Components/Footer";
import RoomPath from "./Components/RoomPath";

var url = "ws://192.168.43.119:8088"; // when using gerard
// var url = "ws://192.168.1.104:8088"; // when using hackathon
// var url = "ws://192.168.43.54:8088"; // menno telefoon
const socket = new WebSocket(url);
function App() {
  var tempTester;
  socket.addEventListener("open", function(event) {
    socket.send("app");
  });
  var t = "H.1.110;H.1.114;H.1.204;H.1.312;H.1.403";
  socket.addEventListener("message", function(event) {
    var res = event.data.split(";").sort();
    tempTester = res.sort();
    alert("Response from webSocket: " + event.data);
    console.log(tempTester);
  });
  socket.onopen = function(event) {
    socket.send("Hello world");
  };
  if (socket.readyState !== socket.OPEN) {
    // console.log("not connected");
    var temp = t.split(";");
    tempTester = temp.sort();
  }
  // tempTester = [];
  // var tempTester = ["first", "second", "third", "fourth"];
  return (
    <div className="site">
      <Header />
      <div className="content">
        {/* <div className="logo">
        <img src={hrlogo} className="logo" alt="hogeschool logo" />
      </div> */}
        <div className="ClassRooms">
          <ClassRooms classrooms={tempTester} />
        </div>
        <div className="UserSelectedRoom">
          <h2>Lokaal reserveren:</h2>
          {tempTester.length < 1 ? (
            <h4>Alle lokalen voor de huidige dag zijn reeds volgeboekt.</h4>
          ) : (
            <Booker value={tempTester} time="08:30" classrooms={tempTester} /> //tempTester[0]
          )}
        </div>
        <div className="Background">
          <img src={Verd1} className="Floor-image" alt="H1 floor" />
        </div>
        <RoomPath />
        <Footer />
      </div>
    </div>
  );
}

function lokaalReservationRequest(lokaal, begintijd, naam, werkplekken) {
  var request = lokaal + ";" + begintijd + ";" + naam + ";" + werkplekken;
  try {
    socket.send(request);
  } catch {
    console.log(
      "There has been a connection error with the server, please try again later."
    );
  }
  // if (socket.readyState === socket.open) socket.send(request);
  // else {
  //   console.log(
  //     "There has been a connection error with the server, please try again later."
  //   );
  // }
}

class Booker extends Component {
  constructor(props) {
    super(props);
    this.state = {
      value: this.props.value[0],
      time: this.props.time,
      naam: "Uw naam",
      aantalWerkplekken: 1
    };

    this.handleChange = this._handleChange;
    this.handleSubmit = this.handleSubmit.bind(this);
  }

  _handleChange = event => {
    console.log(event);

    this.setState({
      value: document.getElementById("lokaal").value,
      time: document.getElementById("begintijd").value,
      naam: document.getElementById("naam").value,
      aantalWerkplekken: document.getElementById("werkplek").value
    });
  };

  handleSubmit(event) {
    event.preventDefault();
    lokaalReservationRequest(
      this.state.value,
      this.state.time,
      this.state.naam,
      this.state.aantalWerkplekken
    );
    alert(
      "A reservation was made for \nRoom: " +
        this.state.value +
        "\nAt: " +
        this.state.time +
        "\nBy: " +
        this.state.naam +
        "\nFor '" +
        this.state.aantalWerkplekken +
        "' person(s)."
    );
  }

  render() {
    return (
      <form className="form" onSubmit={this.handleSubmit}>
        <div>
          <label>Lokaal:</label>
          <select onChange={this.handleChange} id="lokaal">
            {this.props.value.map(function(room, index) {
              return (
                <option key={index} value={room}>
                  {room}
                </option>
              );
            })}
          </select>
        </div>
        <div>
          <label>Begintijd:</label>
          <input
            id="begintijd"
            type="time"
            value={this.state.time}
            onChange={this.handleChange}
          />
        </div>
        <div>
          <label>Naam:</label>
          <input
            id="naam"
            type="text"
            value={this.state.naam}
            onChange={this.handleChange}
          />
        </div>
        <div>
          <label># Werkplekken</label>
          <input
            id="werkplek"
            type="number"
            value={this.state.aantalWerkplekken}
            onChange={this.handleChange}
          />
        </div>
        <div>
          <input type="submit" value="submit" />
        </div>
      </form>
    );
  }
}

export default App;
