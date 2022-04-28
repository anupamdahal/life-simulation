import { useState, useEffect } from "react";
import { Link } from "react-router-dom";
import { getScores } from "../services/services";

const TableData = ({ data }) => {
  return (
    <tr key={data.name}>
      <td>{data.name}</td>
      <td>{data.score}</td>
    </tr>
  )
}

const Scoreboard = () => {


  // TODO: fetch from local cache, and replace the below hard coded array
  const scores = getScores().sort((a, b) => a.score - b.score)

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
            scores.map(score => <TableData key={score.name} data={score} />)
          }
        </tbody>
      </table>
    </div>
  )
}

export default Scoreboard