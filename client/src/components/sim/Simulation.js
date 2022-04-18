import produce from "immer"
import { useState, useRef, useCallback } from "react"
import { useLocation } from "react-router-dom"
import SimCell from "./SimCell"

const Simulation = () => {
  const location = useLocation()
  const entities = location?.state?.entities
  console.log(entities)

  // grid needs to be set to the first generation of the entities grid
  // I can't tell what the shape of entities is, so change this code below to
  // correctly set the initial generation (aka the user configured grid from the prev page)
  // if the following isn't working
  const [grid, setGrid] = useState(entities[0])

  const [running, setRunning] = useState(false)

  const runningRef = useRef(running)
  runningRef.current = running

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
    gridTemplateColumns: `repeat(${entities[0].length}, 20px)`, // again, may not be right
    gridTemplateRows: `repeat(${[entities.lenght]}, 20px)`,     // again, may not be right
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

      </button>
      <div>
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