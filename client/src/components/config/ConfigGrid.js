import React, { useState } from "react"
import produce from "immer"
import Cell from "./grid/Cell"

const ConfigGrid = ({ grid, setGridRef, entityKind }) => {

  const cellClick = (x, y) => {
    const newGrid = produce(grid, gridCopy => {
      switch (entityKind) {
        case "predator":
          gridCopy[x][y] = 1 === grid[x][y] ? 0 : 1
          break
        case "grazer":
          gridCopy[x][y] = 2 === grid[x][y] ? 0 : 2
          break
        case "plant":
          gridCopy[x][y] = 3 === grid[x][y] ? 0 : 3
          break
        case "obstacle":
          gridCopy[x][y] = 4 === grid[x][y] ? 0 : 4
          break
      }
      return gridCopy
    })
    setGridRef(newGrid)
  }

  const gridStyle = {
    display: "grid",
    gridTemplateColumns: `repeat(${grid[0].length}, 20px)`,
    gridTemplateRows: `repeat(${grid.length}, 20px)`,
    width: "60rem",
    height: "73vh",
    overflow: "scroll"
  }

  return (
    <div style={gridStyle}>
      {grid.map((rows, x) =>
        rows.map((col, y) =>
          <Cell
            key={`${x}-${y}`}
            x={x}
            y={y}
            cellClickRef={cellClick}
            isActive={grid[x][y]}
            entityKind={grid[x][y]}
          />)
      )}
    </div>
  )
}

export default ConfigGrid