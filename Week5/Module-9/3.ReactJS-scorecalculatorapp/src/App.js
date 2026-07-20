import './App.css';
import CalculateScore from './Components/CalculateScore';

function App() {
  return (
    <div className="App">
      <CalculateScore
        Name="Jashmitha"
        School="ABC Public School"
        Total={480}
        Goal={500}
      />
    </div>
  );
}

export default App;