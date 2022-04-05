const Plant = ({configs, handler}) => {
  return (
    <div className="configs">
      <div>
        <label>Initial Count: </label>
        <input 
          type="number" 
          min="0" 
          max="50" 
          step="1" 
          name="initialCount"
          value={configs.initialCount}
          onChange={handler}
        />
      </div>
      <div>
        <label>Growth Rate: </label>
        <input 
          type="number" 
          min="0.1" 
          max="1.0" 
          step=".01"
          name="growthRate"
          value={configs.growthRate}
          onChange={handler}
        />
      </div>
      <div>
        <label>Max size: </label>
        <input 
          type="number" 
          min="25" 
          max="100" 
          step="5"
          name="maxSize"
          value={configs.maxSize}
          onChange={handler}
        />
      </div>
      <div>
        <label>Max Seed Cast Distance: </label>
        <input 
          type="number" 
          min="50" 
          max="350" 
          step="50"
          name="maxSeedCastDist"
          value={configs.maxSeedCastDist}
          onChange={handler}
        />
      </div>
      <div>
        <label>Max Seed Count: </label>
        <input 
          type="number" 
          min="1" 
          max="25" 
          step="1"
          name="maxSeedCount"
          value={configs.maxSeedCount}
          onChange={handler}
        />
      </div>
      <div>
        <label>Seed Variability: </label>
        <input 
          type="number" 
          min=".1" 
          max="1.0" 
          step=".1"
          name="seedVariability"
          value={configs.seedVariability}
          onChange={handler}
        />
      </div>
    </div>
  )
}

export default Plant