const Obstacle = ({configs, handler}) => {
  return (
    <div className="configs">
      <div>
        <label>Obstacle Count: </label>
        <input
          type="number"
          min="1"
          max="25"
          step="1"
          value={configs}
          onChange={handler}
        />
      </div>
    </div>
  )
}

export default Obstacle