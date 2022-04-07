const Selection = ({ newEntityRef }) => {

  return (
    <div>
      <div>
        <div>
          <label>Plant</label>
          <input type="radio" name="entity" value="plant" defaultChecked onClick={newEntityRef} />

          <label>Predator</label>
          <input type="radio" name="entity" value="predator" onClick={newEntityRef} />

          <label>Grazer</label>
          <input type="radio" name="entity" value="grazer" onClick={newEntityRef} />

          <label>Obstacle</label>
          <input type="radio" name="entity" value="obstacle" onClick={newEntityRef} />
        </div>
      </div>
    </div>
  )
}

export default Selection