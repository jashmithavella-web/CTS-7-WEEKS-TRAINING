import React from 'react';

function ListofPlayers(){

    const players=[

        {name:"Virat Kohli",score:90},
        {name:"Rohit Sharma",score:85},
        {name:"Shubman Gill",score:75},
        {name:"KL Rahul",score:68},
        {name:"Hardik Pandya",score:72},
        {name:"Ravindra Jadeja",score:60},
        {name:"R Ashwin",score:55},
        {name:"Mohammed Shami",score:45},
        {name:"Jasprit Bumrah",score:65},
        {name:"Surya Kumar",score:88},
        {name:"Ishan Kishan",score:78}

    ];

    const lowScorePlayers=players.filter(player=>player.score<70);

    return(

        <div>

            <h2>List of Players</h2>

            <table border="1" align="center">

                <thead>

                    <tr>
                        <th>Player Name</th>
                        <th>Score</th>
                    </tr>

                </thead>

                <tbody>

                {
                    players.map((player,index)=>

                        <tr key={index}>
                            <td>{player.name}</td>
                            <td>{player.score}</td>
                        </tr>

                    )
                }

                </tbody>

            </table>

            <br/>

            <h2>Players Scored Below 70</h2>

            <ul>

            {
                lowScorePlayers.map((player,index)=>

                    <li key={index}>
                        {player.name} - {player.score}
                    </li>

                )
            }

            </ul>

        </div>

    );

}

export default ListofPlayers;