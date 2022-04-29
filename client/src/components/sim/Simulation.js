import produce from "immer"
import { cloneDeep } from "lodash"
import { useState, useRef, useCallback, useEffect } from "react"
import { useLocation } from "react-router-dom"
import { useNavigate } from "react-router"

import { fetchEntities } from "../../services/fetchEntities"
import { safeResolve } from "../../services/safeResolve"
import { NO_OF_FRAMES } from "../../const/simulatorParams"
import SimCell from "./SimCell"
import { addScore } from "../../utils/scores"

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

  const [resolution, setResolution] = useState(0)
  const [finished, setFinished] = useState(false)

  useEffect(() => {
    const initialFetch = async () => {
      const [entities, err] = await safeResolve(fetchEntities(request))
      if (err) {
        // handle error
        return
      }
      setStart(true)
      if (entities.length === 0) return
      totalGens.current += entities.length
      curEntities.current = entities
      setGrid(entities[0])
      const colRes = entities[0][0].length >= 75 ? 10 : 15
      const rowRes = entities[0].length >= 75 ? 10 : 15
      setResolution(Math.min(colRes, rowRes))
    }
    initialFetch()
  }, [])

  const runSimulation = useCallback(async gen => {
    if (curEntities.current.length === 0 || !runningRef.current) {
      setFinished(true)
      return
    }

    if (gen !== 0 && gen % NO_OF_FRAMES === 0) {
      if (newEntities.current.length === 0) {
        runningRef.current = false
        setFinished(true)
        return
      }
      curEntities.current = newEntities.current
      newEntities.current = []
    }

    if (gen >= totalGens.current) {
      runningRef.current = false
      setFinished(true)
      return
    }

    if (gen % NO_OF_FRAMES === 1) {
      const newRequest = cloneDeep(request)
      newRequest.entities = cloneDeep(curEntities.current[curEntities.current.length - 1])

      fetchEntities(newRequest)
        .then(entities => {
          totalGens.current += entities.length
          newEntities.current = entities
        })
        .catch(err => {
          console.error(err)
          // handle error
        })
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

  const [username, setUsername] = useState("")

  const handleNameChange = event => {
    event.preventDefault()
    setUsername(event.target.value)
  }


  const submitScore = event => {
    if (!finished) {
      window.alert("SIMULATION IS NOT FINISHED")
      return
    }
    event.preventDefault()
    addScore(username, totalGens.current)
  }

  return (
    <>
      {
        start && grid &&
        <div className="simulation-wrapper">
          <div className="simulation" >
            <button
              onClick={() => {
                setRunning(!running)
                if (!running) {
                  runningRef.current = true
                  runSimulation(1)
                }
              }}>
              {running.current ? "stop" : "start"}
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
              gridTemplateColumns: `repeat(${grid[0].length}, ${resolution}px)`,
              gridTemplateRows: `repeat(${[grid.length]}, ${resolution}px)`,
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
                    resolution={resolution}
                  />)
              )}
            </div>
          </div>
          <div >
            <form onSubmit={submitScore} className="score-enter">
              <label>Name: </label>
              <input type="text" value={username} onChange={handleNameChange}></input>
              <button type="submit">Submit</button>
            </form>
          </div>
        </div>
      }
    </>
  )
}

export default Simulation