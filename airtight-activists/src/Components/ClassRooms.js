import React, { Component } from "react";

import "./ClassRooms.css";

var url = "ws://192.168.43.54:8088"; // menno telefoon
const socket = new WebSocket(url);
socket.addEventListener("open", function(event) {
  socket.send("classroom");
});

export default class ClassRooms extends Component {
  render() {
    // return (
    //   <div>
    //     <h2>{header}</h2>
    //     <h4>Momenteel zijn alle lokalen in gebruik.</h4>
    //   </div>
    // );
    const header = "Beschikbare lokalen:";
    if (this.props.classrooms.length < 2) {
      return (
        <div>
          <h2>{header}</h2>
          <h4>Momenteel zijn alle lokalen in gebruik.</h4>
        </div>
      );
    } else {
      var classroomList = this.props.classrooms.map(function(room, index) {
        var tempClass = "right";
        if (index % 2 === 0) {
          tempClass = "left";
        }
        return (
          <li className={tempClass} key={index}>
            {room}
          </li>
        );
      });
      return (
        <div>
          <h2>{header}</h2>
          <ul className="NoDot">{classroomList}</ul>
        </div>
      );
    }
  }
}
