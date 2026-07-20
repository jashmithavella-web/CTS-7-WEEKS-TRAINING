import React from "react";

function FlightDetails() {

    return (

        <div>

            <h2>Flight Details</h2>

            <table border="1">

                <thead>

                    <tr>

                        <th>Flight</th>
                        <th>From</th>
                        <th>To</th>
                        <th>Price</th>

                    </tr>

                </thead>

                <tbody>

                    <tr>
                        <td>AI101</td>
                        <td>Hyderabad</td>
                        <td>Delhi</td>
                        <td>₹5500</td>
                    </tr>

                    <tr>
                        <td>6E202</td>
                        <td>Bangalore</td>
                        <td>Mumbai</td>
                        <td>₹4200</td>
                    </tr>

                    <tr>
                        <td>UK303</td>
                        <td>Chennai</td>
                        <td>Kolkata</td>
                        <td>₹6100</td>
                    </tr>

                </tbody>

            </table>

        </div>

    );

}

export default FlightDetails;