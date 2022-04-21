import { useState } from "react"
import { useNavigate } from "react-router-dom"
import Land from "./form/Land"
import Plant from "./form/Plant"
import Grazer from "./form/Grazer"
import Predator from "./form/Predator"
import Obstacle from "./form/Obstacle"

const ConfigForm = ({ newGridSizeRef, handleSubmitRef }) => {

  const [landConfig, setLandConfig] = useState({
    landX: 100,
    landY: 75
  })

  const [plantConfig, setPlantConfig] = useState({
    growthRate: 0.1,
    maxSize: 100,
    maxSeedCastDist: 250,
    maxSeedCount: 10,
    seedVariability: .50,
    diameter: 25,
  })

  const [grazerConfig, setGrazerConfig] = useState({
    initialEnergy: 50,
    energyInput: 5,
    energyOutput: 1,
    energyToReproduce: 100,
    baseSpeed: 3,
    maxSpeed: 20,
    maintainSpeedTime: 3,
  })

  const [predatorConfig, setPredatorConfig] = useState({
    initialEnergy: 150,
    maxSpeedHOD: 20,
    maxSpeedHED: 18,
    maxSpeedHOR: 15,
    baseSpeed: 4,
    energyOutput: 10,
    energyToReproduce: 500,
    maxOffspring: 3,
    gestationPeriod: 5,
    offspringEnergy: 150,
    maintainSpeedTime: 5
  })

  const [obstacleConfig, setObstacleConfig] = useState({
    diameter: 50,
    height: 10,
  })

  const handleLandChange = event => {
    const temp = {
      ...landConfig
    }

    temp[event.target.name] = +event.target.value

    setLandConfig(temp)
    newGridSizeRef(temp.landY, temp.landX)
  }

  const handlePlantChange = event => {
    const temp = {
      ...plantConfig
    }

    temp[event.target.name] = +event.target.value

    setPlantConfig(temp)
  }

  const handleGrazerChange = event => {
    const temp = {
      ...grazerConfig
    }

    temp[event.target.name] = +event.target.value

    setGrazerConfig(temp)
  }

  const handlePredatorChange = event => {
    const temp = {
      ...predatorConfig
    }

    temp[event.target.name] = +event.target.value

    setPredatorConfig(temp)
  }

  const handleObstacleChange = event => {
    const temp = {
      ...obstacleConfig
    }

    temp[event.target.name] = +event.target.value

    setObstacleConfig(temp)
  }

  const [hideConfigs, setHideConfigs] = useState({
    land: true,
    plant: true,
    grazer: true,
    predator: true,
    obstacle: true,
  })

  const handleHideToggle = event => {
    const temp = {
      ...hideConfigs
    }

    temp[event.target.name] = !temp[event.target.name]

    setHideConfigs(temp)
  }

  const navigate = useNavigate()

  const submitHelper = (event) => {
    event.preventDefault()
    handleSubmitRef({
      landConfig,
      plantConfig,
      predatorConfig,
      obstacleConfig,
      grazerConfig
    })
  }

  return (
    <form onSubmit={submitHelper} className="config-form">
      <div className="config-header">
        <h2>Land Bounds: </h2>
        <button type="button" onClick={handleHideToggle} name="land" className="hide-toggle">
          {
            hideConfigs.land ?
              "Hide"
              : "Show"
          }
        </button>
      </div>
      {
        hideConfigs.land ?
          <Land
            configs={landConfig}
            handler={handleLandChange}
          />
          : null
      }
      <div className="config-header">
        <h2>Plants: </h2>
        <button type="button" onClick={handleHideToggle} name="plant" className="hide-toggle">
          {
            hideConfigs.plant ?
              "Hide"
              : "Show"
          }
        </button>
      </div>
      {
        hideConfigs.plant ?
          <Plant
            configs={plantConfig}
            handler={handlePlantChange}
          />
          : null
      }
      <div className="config-header">
        <h2>Grazers: </h2>
        <button type="button" onClick={handleHideToggle} name="grazer" className="hide-toggle">
          {
            hideConfigs.grazer ?
              "Hide"
              : "Show"
          }
        </button>
      </div>
      {
        hideConfigs.grazer ?
          <Grazer
            configs={grazerConfig}
            handler={handleGrazerChange}
          />
          : null
      }
      <div className="config-header">
        <h2>Predators: </h2>
        <button type="button" onClick={handleHideToggle} name="predator" className="hide-toggle">
          {
            hideConfigs.predator ?
              "Hide"
              : "Show"
          }
        </button>
      </div>
      {
        hideConfigs.predator ?
          <Predator
            configs={predatorConfig}
            handler={handlePredatorChange}
          />
          : null
      }
      <div className="config-header">
        <h2>Obstacles: </h2>
        <button type="button" onClick={handleHideToggle} name="obstacle" className="hide-toggle">
          {
            hideConfigs.obstacle ?
              "Hide"
              : "Show"
          }
        </button>
      </div>
      {
        hideConfigs.obstacle ?
          <Obstacle
            configs={obstacleConfig}
            handler={handleObstacleChange}
          />
          : null
      }
      <div className="submit-container">
        <button type="submit" className="submit-btn">Submit</button>
      </div>
    </form>
  )
}

export default ConfigForm