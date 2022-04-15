const Obstacle = ({ configs, handler }) => {
  return (
    <div className="configs">
      <div>
        <label>Obstacle Diameter: </label>
        <input
          type="number"
          min="25"
          max="75"
          step="5"
          name="diameter"
          value={configs.diameter}
          onChange={handler}
        />
      </div>
      <div>
        <label>Obstacle Height: </label>
        <input
          type="number"
          min="5"
          max="25"
          step="5"
          name=""
          value={configs.height}
          onChange={handler}
        />
      </div>
    </div>
  )
}

export default Obstacle