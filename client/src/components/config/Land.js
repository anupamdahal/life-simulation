const Land = ({configs, handler}) => {
  return (
    <div className="configs">
      <div>
        <label>X bound: </label>
        <input 
          type="number" 
          min="500" 
          max="1000" 
          step="50" 
          name="landX"
          value={configs.landX}
          onChange={handler}
        />
      </div>
      <div>
        <label>Y bound: </label>
        <input 
          type="number" 
          min="500" 
          max="1000" 
          step="50" 
          name="landY"
          value={configs.landY}
          onChange={handler}
        />
      </div>
    </div>
  )
}

export default Land