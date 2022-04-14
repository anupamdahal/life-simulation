import ConfigGrid from "./ConfigGrid"
import Selection from "./grid/Selection"
import ConfigForm from "./ConfigForm"
import { useState } from "react"
import { useNavigate } from "react-router"
import services from "../../services/services"

const ConfigWrapper = () => {

  const initGrid = (rows, cols) => {
    return new Array(rows).fill(new Array(cols).fill(0))
  }

  const [grid, setGrid] = useState(initGrid(25, 40))
  const [entity, setEntity] = useState("plant")

  const newGridSize = (rows, cols) => {
    setGrid(initGrid(rows, cols))
  }

  const newEntity = event => {
    setEntity(event.target.value)
    console.log(entity)
  }

  const navigate = useNavigate()

  const handleSubmit = configs => {

    const temp = {
      ...configs,
      entities: grid
    }

    console.log(JSON.stringify(temp))

    services
      .postConfigs(temp)
      .then(res => console.log(res))

    navigate("/simulation")
  }

  return (
    <div className="config-container">
      <ConfigForm newGridSizeRef={newGridSize} handleSubmitRef={handleSubmit} />
      <div>
        <Selection newEntityRef={newEntity} />
        <ConfigGrid grid={grid} setGridRef={setGrid} entityKind={entity} />
      </div>
    </div>
  )
}

export default ConfigWrapper