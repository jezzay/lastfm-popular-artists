import React, { Component } from 'react';
import './App.css';
import ArtistGeoLookup from './ArtistGeoLookup'


class App extends Component {
  render() {
    return (
      <div className="App">
        <div className="App-header">
          <h2>Welcome to Last FM Artists lookup</h2>
        </div>
        <div className="App-intro">
            <div className="container-fluid">
                <ArtistGeoLookup/>
            </div>
        </div>
      </div>
    );
  }
}

export default App;
