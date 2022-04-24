import produce from "immer"
import { useState, useRef, useCallback, useEffect } from "react"
import { useLocation } from "react-router-dom"
import { fetchEntities } from "../../services/fetchEntities"
import { safeResolve } from "../../services/safeResolve"
import { NO_OF_FRAMES } from "../../const/simulatorParams"
import SimCell from "./SimCell"

const numRows = 25
const numCols = 25

const Simulation = () => {
  const location = useLocation()
  const request = location?.state?.request

  const [totalGens, setTotalGens] = useState([])
  const [grid, setGrid] = useState([])
  const [curEntities, setCurEntities] = useState([]) 
  const [newEntities, setNewEntities] = useState([])

  const [start, setStart] = useState(false)
  const [running, setRunning] = useState(false)

  const runningRef = useRef(running)
  runningRef.current = running

  useEffect(() => {
    const initialFetch = async () => {
      const [entities, err] = await safeResolve(fetchEntities(request))
      if(err){
        // handle error
        return 
      }
      setTotalGens((prevCount) => prevCount + entities?.length)
      setCurEntities(entities)
      setGrid(entities[0])
      setStart(true)
    }
    initialFetch()
  }, [])

  const runSimulation = useCallback( async gen => {
    console.log("length")
    if(curEntities.length === 0) return

    console.log("running")
    if (!runningRef.current) {
      return
    }

    console.log("swap frames")
    if(gen !== 0 && gen % NO_OF_FRAMES === 0){
      if(newEntities.length === 0){
        return
      }
      setCurEntities(newEntities)
      setNewEntities([])
    }

    console.log("ran for specficed gens")
    if (gen >= totalGens) {
      runningRef.current = false
      return
    }

    console.log("get more frames")
    if(gen % NO_OF_FRAMES === 1){
      const newRequest = {
        ...request,
        grid: curEntities[curEntities.length - 1]
      }
      const [entities, err] = safeResolve(fetchEntities(newRequest))
      if(err){
        // handle error
        return 
      }
      setTotalGens((prevCount) => prevCount + entities?.length)
      setNewEntities(entities)
    }

    setGrid(g => {
      return produce(g, gridCopy => {
        for (let i = 0; i < curEntities[0].length; i++) {
          for (let k = 0; k < curEntities[0][0].length; k++) {
            gridCopy[i][k] = curEntities[gen][i][k]
          }
        }
      })
    })
    setTimeout(() => runSimulation(gen + 1), 100)
  }, [])

  return (
    <div className="simulation-wrapper">
      {
        start &&
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
          <div style={{
            display: "grid",
            gridTemplateColumns: `repeat(${grid[0].length}, 15px)`,
            gridTemplateRows: `repeat(${[grid.length]}, 15px)`,
            width: "70rem",
            height: "73vh",
            overflow: "scroll",
            resize: "both"
          }}>
            {grid.map((rows, x) =>
              rows.map((col, y) =>
                <SimCell
                  key={`${x}-${y}`}
                  entityKind={grid[x][y]}
                />)
            )}
          </div>
        </>
      }
    </div>
  )
}

export default Simulation