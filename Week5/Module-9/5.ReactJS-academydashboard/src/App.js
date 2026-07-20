import './App.css';
import CohortDetails from './Components/CohortDetails';

function App() {

  const cohorts = [

    {
      name: "React Fundamentals",
      mentor: "John",
      startDate: "01-Jul-2026",
      status: "Ongoing"
    },

    {
      name: "Java Full Stack",
      mentor: "David",
      startDate: "10-Jun-2026",
      status: "Completed"
    },

    {
      name: "Spring Boot",
      mentor: "Smith",
      startDate: "15-Jul-2026",
      status: "Ongoing"
    }

  ];

  return (

    <div>

      <h1 align="center">Academy Dashboard</h1>

      {
        cohorts.map((item,index)=>

          <CohortDetails key={index} cohort={item}/>

        )
      }

    </div>

  );

}

export default App;