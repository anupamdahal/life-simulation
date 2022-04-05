const Grazer = ({configs, handler}) => {
  return (
    <div className="configs">
      <div>
        <label>Initial Count: </label>
        <input
          type="number"
          min="1"
          max="25"
          step="1"
          name="initialCount"
          value={configs.initialCount}
          onChange={handler}
        />
      </div>
      <div>
        <label>Initial Energy: </label>
        <input
          type="number"
          min="25"
          max="100"
          step="5"
          name="initialEnergy"
          value={configs.initialEnergy}
          onChange={handler}
        />
      </div>
      <div>
        <label>Energy Input: </label>
        <input
          type="number"
          min="1"
          max="15"
          step="1"
          name="energyInput"
          value={configs.energyInput}
          onChange={handler}
        />
      </div>
      <div>
        <label>Energy Output: </label>
        <input
          type="number"
          min="1"
          max="15"
          step="1"
          name="energyOutput"
          value={configs.energyOutput}
          onChange={handler}
        />
      </div>
      <div>
        <label>Energy To Reproduce: </label>
        <input
          type="number"
          min="50"
          max="150"
          step="10"
          name="energyToReproduce"
          value={configs.energyToReproduce}
          onChange={handler}
        />
      </div>
      <div>
        <label>Base Speed: </label>
        <input
          type="number"
          min="1"
          max="5"
          step="1"
          name="baseSpeed"
          value={configs.baseSpeed}
          onChange={handler}
        />
      </div>
      <div>
        <label>Max Speed: </label>
        <input
          type="number"
          min="5"
          max="25"
          step="1"
          name="maxSpeed"
          value={configs.maxSpeed}
          onChange={handler}
        />
      </div>
    </div>
  )
}

export default Grazer