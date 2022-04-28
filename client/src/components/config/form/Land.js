const Land = ({ configs, handler }) => {
  return (
    <div className="configs">
      <div>
        <label>X bound: </label>
        <input
          type="number"
          min="10"
          max="100"
          step="5"
          name="landX"
          value={configs.landX}
          onChange={handler}
        />
      </div>
      <div>
        <label>Y bound: </label>
        <input
          type="number"
          min="10"
          max="100"
          step="5"
          name="landY"
          value={configs.landY}
          onChange={handler}
        />
      </div>
    </div>
  )
}

export default Land