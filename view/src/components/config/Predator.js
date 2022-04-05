const Predator = ({configs, handler}) => {
  return (
    <div className="configs">
      <div>
        <label>Initial Count: </label>
        <input
          type="number"
          min="1"
          max="10"
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
          min="50"
          max="200"
          step="10"
          name="initialEnergy"
          value={configs.initialEnergy}
          onChange={handler}
        />
      </div>
      <div>
        <label>Max Speed HOD: </label>
        <input
          type="number"
          min="10"
          max="25"
          step="1"
          name="maxSpeedHOD"
          value={configs.maxSpeedHOD}
          onChange={handler}
        />
      </div>
      <div>
        <label>Max Speed HED: </label>
        <input
          type="number"
          min="10"
          max="23"
          step="1"
          name="maxSpeedHED"
          value={configs.maxSpeedHED}
          onChange={handler}
        />
      </div>
      <div>
        <label>Max Speed HOR: </label>
        <input
          type="number"
          min="10"
          max="20"
          step="1"
          name="maxSpeedHOR"
          value={configs.maxSpeedHOR}
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
          min="100"
          max="500"
          step="50"
          name="energyToReproduce"
          value={configs.energyToReproduce}
          onChange={handler}
        />
      </div>
      <div>
        <label>Max Offspring: </label>
        <input
          type="number"
          min="1"
          max="5"
          step="1"
          name="maxOffspring"
          value={configs.maxOffspring}
          onChange={handler}
        />
      </div>
      <div>
        <label>Gestation Period: </label>
        <input
          type="number"
          min="1"
          max="10"
          step="1"
          name="gestationPeriod"
          value={configs.gestationPeriod}
          onChange={handler}
        />
      </div>
      <div>
        <label>Offspring Energy: </label>
        <input
          type="number"
          min="50"
          max="200"
          step="10"
          name="offspringEnergy"
          value={configs.offspringEnergy}
          onChange={handler}
        />
      </div>
    </div>
  )
}

export default Predator