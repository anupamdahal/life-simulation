import ConfigGrid from "./ConfigGrid"
import Selection from "./grid/Selection"
import ConfigForm from "./ConfigForm"
import { useState } from "react"
import { useNavigate } from "react-router"
import services, { getScores } from "../../services/services"
import { produce } from "immer"
import { safeResolve } from "../../services/safeResolve"

const ConfigWrapper = () => {

  const initGrid = (rows, cols) => {
    return new Array(rows).fill(new Array(cols).fill(0))
  }

  const resizeGrid = (rows, cols) => {

    let newGrid
    if (rows < grid.length) {
      newGrid = produce(grid, newGrid => {
        return newGrid = newGrid.slice(0, -5)
      })
    } else if (cols < grid[0].length) {
      newGrid = produce(grid, newGrid => {
        return newGrid = newGrid.map(arr => arr.slice(0, -5))
      })
    } else if (rows > grid.length) {
      newGrid = produce(grid, newGrid => {
        return newGrid = newGrid.concat(new Array(5).fill(new Array(cols).fill(0)))
      })
    } else if (cols > grid[0].length) {
      newGrid = produce(grid, newGrid => {
        return newGrid = newGrid.map(arr => arr.concat(new Array(5).fill(0)))
      })
    }
    return newGrid
  }

  const [grid, setGrid] = useState(initGrid(75, 100))
  const [entity, setEntity] = useState("plant")

  const newGridSize = (rows, cols) => {
    setGrid(resizeGrid(rows, cols))
  }

  const newEntity = event => {
    setEntity(event.target.value)
  }

  const navigate = useNavigate()

  const handleSubmit = async configs => {

    const temp = {
      ...configs,
      entities: grid
    }

    const [entities, err] = await safeResolve(
      services
        .postConfigs(temp)
        .then(res => {
          if (!res?._id) {
            return Promise.reject("Request Id Not Received")
          }
          return res._id
        })
        .then(id => getScores(id))
        .then(simResult => {
          if (simResult.error !== "None") {
            return Promise.reject(simResult.error)
          }
          return simResult.entities
        })
    )

    if (err) {
      // TODO: Handle Error
      console.error(err)
      return
    }

    navigate("/simulation", { state: { entities } })
  }

  return (
    <div className="config-container">
      <ConfigForm newGridSizeRef={newGridSize} handleSubmitRef={handleSubmit} />
      <div>
        <Selection newEntityRef={newEntity} />
        <ConfigGrid
          grid={grid}
          setGridRef={setGrid}
          entityKind={entity}
        />
      </div>
    </div>
  )
}

export default ConfigWrapper