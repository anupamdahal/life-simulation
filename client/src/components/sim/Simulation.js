import produce from "immer"
import { cloneDeep } from "lodash"
import { useState, useRef, useCallback, useEffect } from "react"
import { useLocation } from "react-router-dom"
import { fetchEntities } from "../../services/fetchEntities"
import { safeResolve } from "../../services/safeResolve"
import { NO_OF_FRAMES } from "../../const/simulatorParams"
import SimCell from "./SimCell"

const Simulation = () => {
  const location = useLocation()
  const request = location?.state?.request
  const [grid, setGrid] = useState(() => request.entities)
  const totalGens = useRef(0)
  const curEntities = useRef([]) 
  const newEntities = useRef([])

  const [start, setStart] = useState(false)
  const [running, setRunning] = useState(false)
  const [simSpeed, setSimSpeed] = useState(1000)

  const runningRef = useRef(running)
  const simSpeedRef = useRef(simSpeed)
  runningRef.current = running
  simSpeedRef.current = simSpeed

  useEffect(() => {
    const initialFetch = async () => {
      const [entities, err] = await safeResolve(fetchEntities(request))
      if(err){
        // handle error
        return 
      }
      setStart(true)
      if(entities.length === 0) return
      totalGens.current += entities.length
      curEntities.current = entities
      setGrid(entities[0])
    }
    initialFetch()
  }, [])

  const runSimulation = useCallback( async gen => {
    if(curEntities.current.length === 0) return

    if (!runningRef.current) {
      return
    }

    if(gen !== 0 && gen % NO_OF_FRAMES === 0){
      console.log("swap frames")
      if(newEntities.current.length === 0){
        runningRef.current = false
        return
      }
      curEntities.current = newEntities.current
      newEntities.current = []
    }

    if (gen >= totalGens.current) {
      runningRef.current = false
      return
    }

    console.log(totalGens.current, gen, gen % NO_OF_FRAMES)
    if(gen % NO_OF_FRAMES === 1){
      const newRequest = cloneDeep(request)
      newRequest.entities = cloneDeep(curEntities.current[curEntities.current.length - 1])

      const [entities, err] = await safeResolve(fetchEntities(newRequest))
      if(err){
        // handle error
        return 
      }
      totalGens.current += entities.length
      newEntities.current = entities
    }

    setGrid(g => {
      return produce(g, gridCopy => {
        for (let i = 0; i < curEntities.current[0].length; i++) {
          for (let k = 0; k < curEntities.current[0][0].length; k++) {
            gridCopy[i][k] = curEntities.current[gen % NO_OF_FRAMES][i][k]
          }
        }
      })
    })
    setTimeout(() => runSimulation(gen + 1), simSpeedRef.current)
  }, [])

  return (
    <div className="simulation-wrapper">
      {
        start && grid &&
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