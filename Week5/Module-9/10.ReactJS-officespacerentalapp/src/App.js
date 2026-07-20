import './App.css';

function App() {

  const office = {
    name: "Smart Tech Office",
    rent: 55000,
    address: "Madhapur, Hyderabad"
  };

  const officeList = [
    {
      id: 1,
      name: "Smart Tech Office",
      rent: 55000,
      address: "Hyderabad"
    },
    {
      id: 2,
      name: "Tech Park",
      rent: 75000,
      address: "Bangalore"
    },
    {
      id: 3,
      name: "Business Hub",
      rent: 45000,
      address: "Chennai"
    }
  ];

  return (
    <div className="App">

      <h1>Office Space Rental Application</h1>

      <img
        src="/office.jpg"
        alt="Office Space"
        width="500"
        height="300"
      />

      <h2>Single Office Details</h2>

      <p><b>Name:</b> {office.name}</p>

      <p
        style={{
          color: office.rent < 60000 ? "red" : "green"
        }}
      >
        <b>Rent:</b> ₹{office.rent}
      </p>

      <p><b>Address:</b> {office.address}</p>

      <hr />

      <h2>Available Office Spaces</h2>

      {
        officeList.map((item) => (

          <div className="card" key={item.id}>

            <h3>{item.name}</h3>

            <p
              style={{
                color: item.rent < 60000 ? "red" : "green"
              }}
            >
              <b>Rent:</b> ₹{item.rent}
            </p>

            <p><b>Address:</b> {item.address}</p>

          </div>

        ))
      }

    </div>
  );
}

export default App;