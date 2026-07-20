import React, { Component } from "react";
import "./App.css";
import Guest from "./Components/Guest";
import User from "./Components/User";

class App extends Component {

  constructor() {
    super();

    this.state = {
      isLoggedIn: false
    };
  }

  login = () => {
    this.setState({ isLoggedIn: true });
  };

  logout = () => {
    this.setState({ isLoggedIn: false });
  };

  render() {

    if (this.state.isLoggedIn) {

      return (
        <div className="App">
          <button onClick={this.logout}>Logout</button>
          <User />
        </div>
      );

    }

    return (
      <div className="App">
        <button onClick={this.login}>Login</button>
        <Guest />
      </div>
    );
  }
}

export default App;