import produce from "immer"
import { useState, useRef, useCallback } from "react"
import { useLocation } from "react-router-dom"
import SimCell from "./SimCell"


const Simulation = () => {
  const location = useLocation()
  const entities = location?.state?.entities
  const [grid, setGrid] = useState(entities[0])
  console.log(grid.length, grid[0].length)

  const [running, setRunning] = useState(false)
  const [simSpeed, setSimSpeed] = useState(1000)

  const runningRef = useRef(running)
  const simSpeedRef = useRef(simSpeed)
  runningRef.current = running
  simSpeedRef.current = simSpeed

  const runSimulation = useCallback(gen => {
    if (!runningRef.current) {
      return
    }

    // I'm unsure whether entites.length is the right call here, again
    // I'm not sure what the shape is. But, the idea here is to check to see
    // if we have looped over every generation, and if so return
    if (gen === entities.length) {
      runningRef.current = false
      return
    }

    setGrid(g => {
      return produce(g, gridCopy => {
        for (let i = 0; i < entities[0].length; i++) {
          for (let k = 0; k < entities[0][0].length; k++) {
            gridCopy[i][k] = entities[gen][i][k]
          }
        }
      })
    })
    setTimeout(() => runSimulation(gen + 1), simSpeedRef.current)
  }, [])

  const gridStyle = {
    display: "grid",
    gridTemplateColumns: `repeat(${grid[0].length}, 20px)`, // again, may not be right
    gridTemplateRows: `repeat(${[grid.length]}, 20px)`,     // again, may not be right
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
            runSimulation(1)
          }
        }}>
        {running ? "stop" : "start"}
      </button>
      <button
        onClick={() => {
          setSimSpeed(1000)
          simSpeedRef.current = 1000
        }}>
        {"x1"}
      </button>
      <button
        onClick={() => {
          setSimSpeed(100)
          simSpeedRef.current = 100
        }}>
        {"x10"}
      </button>
      <button
        onClick={() => {
          setSimSpeed(20)
          simSpeedRef.current = 20
        }}>
        {"x50"}
      </button>
      <button
        onClick={() => {
          setSimSpeed(10)
          simSpeedRef.current = 10
        }}>
        {"x100"}
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