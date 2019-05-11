import React, { Component } from "react";

import hrlogo from "../images/school-logo.png";

import "./Header.css";

export default class Header extends Component {
  render() {
    return (
      <header>
        <div className="logo">
          <img src={hrlogo} className="logo" alt="hogeschool logo" />
        </div>
        <h2>Lokalenboeker</h2>
      </header>
    );
  }
}
