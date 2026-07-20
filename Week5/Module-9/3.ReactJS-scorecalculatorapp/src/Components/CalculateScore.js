import React from 'react';
import '../Stylesheets/mystyle.css';

function CalculateScore(props) {

    const average = ((props.Total / props.Goal) * 100).toFixed(2);

    return (

        <div className="container">

            <h1>Student Score Calculator</h1>

            <h2>Student Details</h2>

            <p><strong>Name :</strong> {props.Name}</p>

            <p><strong>School :</strong> {props.School}</p>

            <p><strong>Total Marks :</strong> {props.Total}</p>

            <p><strong>Goal :</strong> {props.Goal}</p>

            <h2>Average Score : {average}%</h2>

        </div>

    );
}

export default CalculateScore;