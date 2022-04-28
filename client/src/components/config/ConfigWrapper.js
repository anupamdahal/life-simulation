import ConfigGrid from "./ConfigGrid"
import Selection from "./grid/Selection"
import ConfigForm from "./ConfigForm"
import { useState } from "react"
import { useNavigate } from "react-router"
import { produce } from "immer"

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

    const request = {
      ...configs,
      entities: grid
    }

    navigate("/simulation", { state: { request } })
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