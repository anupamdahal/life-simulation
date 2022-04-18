import produce from "immer"
import { useState, useRef, useCallback } from "react"
import { useLocation } from "react-router-dom"
import SimCell from "./SimCell"

const numRows = 25
const numCols = 25

const Simulation = () => {
  const location = useLocation()
  const entities = location?.state?.entities

  // this is not the final result we still need to
  // fetch the simulation result from the backend
  // and then loop over it instead of this dummy data

  const generateRandGrid = () => {
    const rows = []
    for (let i = 0; i < numRows; i++) {
      rows.push(Array.from(Array(numCols), () => Math.floor(Math.random() * 5)))
    }
    return rows
  }

  const generateEmptyGrid = () => {
    const rows = []
    for (let i = 0; i < numRows; i++) {
      rows.push(Array.from(Array(numCols), () => 0))
    }

    return rows
  }

  let sim = []
  for (let i = 0; i < 10; i++) {
    sim.push(generateRandGrid())
  }

  const [grid, setGrid] = useState(generateEmptyGrid())

  const [running, setRunning] = useState(false)

  const runningRef = useRef(running)
  runningRef.current = running

  const runSimulation = useCallback(gen => {
    if (!runningRef.current) {
      return
    }

    if (gen === 10) {
      runningRef.current = false
      return
    }

    setGrid(g => {
      return produce(g, gridCopy => {
        for (let i = 0; i < numRows; i++) {
          for (let k = 0; k < numCols; k++) {
            gridCopy[i][k] = sim[gen][i][k]
          }
        }
      })
    })
    setTimeout(() => runSimulation(gen + 1), 100)
  }, [])

  const gridStyle = {
    display: "grid",
    gridTemplateColumns: `repeat(${numCols}, 20px)`,
    gridTemplateRows: `repeat(${numRows}, 20px)`,
    width: "60rem",
    height: "73vh",
    overflow: "scroll"
  }

  return (
    <>
      <button
        onClick={() => {
          setRunning(!running)
          if (!running) {
            runningRef.current = true
            runSimulation(0)
          }
        }}>
        {running ? "stop" : "start"}
      </button>
      <div style={gridStyle}>
        {grid.map((rows, x) =>
          rows.map((col, y) =>
            <SimCell
              key={`${x}-${y}`}
              entityKind={grid[x][y]}
            />)
        )}
      </div>
    </>
  )
}

export default Simulation