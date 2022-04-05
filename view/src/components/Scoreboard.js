import { useState, useEffect } from "react";
import { Link } from "react-router-dom";

const TableData = ({data}) => {
  return (
    <tr key={data.name}>
      <td>{data.name}</td>
      <td>{data.score}</td>
    </tr>
  )
}

const Scoreboard = () => {

  const names = [
    {
      name: "JT", 
      score: 99
    },
    {
      name: "Nick",
      score: 93
    },
    {
      name: "Anupam",
      score: 95
    },
    {
      name: "Ethan",
      score: 90
    }
  ]

  return (
    <div className="scoreboard">
      <h1 className="scoreboard-header">Scoreboard</h1>
      <table>
        <tbody>
          <thead>
            <th>Name</th>
            <th>Score</th>
          </thead>
          {
            names.map(score => <TableData key={score.name} data={score} />)
          }
        </tbody>
      </table>
    </div>
  )
}

export default Scoreboard