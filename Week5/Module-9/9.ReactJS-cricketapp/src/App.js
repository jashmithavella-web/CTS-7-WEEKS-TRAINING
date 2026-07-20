import './App.css';
import ListofPlayers from './Components/ListofPlayers';
import IndianPlayers from './Components/IndianPlayers';

function App() {

  const flag = true;      // Change to false to see IndianPlayers component

  if(flag){
    return(
      <div className="App">
        <h1>Cricket App</h1>
        <ListofPlayers/>
      </div>
    );
  }
  else{
    return(
      <div className="App">
        <h1>Cricket App</h1>
        <IndianPlayers/>
      </div>
    );
  }

}

export default App;