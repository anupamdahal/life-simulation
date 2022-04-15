import React, { useState } from "react"
import produce from "immer"
import Cell from "./grid/Cell"

const ConfigGrid = ({ grid, setGridRef, entityKind, updateEntityCountRef }) => {

  const cellType = (row, col) => {
    switch (grid[row][col]) {
      case 0:
        return null
      case 1:
        return "predators"
      case 2:
        return "grazers"
      case 3:
        return "plants"
      case 4:
        return "obstacles"
    }
  }

  const cellClick = (x, y) => {
    const newGrid = produce(grid, gridCopy => {
      let type = cellType(x, y)
      switch (entityKind) {
        case "predator":
          gridCopy[x][y] = 1 === grid[x][y] ? 0 : 1
          updateEntityCountRef("predators", type)
          break
        case "grazer":
          gridCopy[x][y] = 2 === grid[x][y] ? 0 : 2
          updateEntityCountRef("grazers", type)
          break
        case "plant":
          gridCopy[x][y] = 3 === grid[x][y] ? 0 : 3
          updateEntityCountRef("plants", type)
          break
        case "obstacle":
          gridCopy[x][y] = 4 === grid[x][y] ? 0 : 4
          updateEntityCountRef("obstacles", type)
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