import React, { useEffect, useState } from "react"
import produce from "immer"
import Cell from "./grid/Cell"

const ConfigGrid = ({ grid, setGridRef, entityKind, updateEntityCountRef }) => {

  const cellClick = (x, y) => {
    const newGrid = produce(grid, gridCopy => {
      let entityVal
      switch (entityKind) {
        case "predator":
          entityVal = 1 === grid[x][y] ? 0 : 1
          gridCopy[x][y] = entityVal
          updateEntityCountRef("predators", entityVal ? 1 : -1)
          break
        case "grazer":
          entityVal = 2 === grid[x][y] ? 0 : 2
          gridCopy[x][y] = entityVal
          updateEntityCountRef("grazers", entityVal ? 1 : -1)
          break
        case "plant":
          entityVal = 3 === grid[x][y] ? 0 : 3
          gridCopy[x][y] = entityVal
          updateEntityCountRef("plants", entityVal ? 1 : -1)
          break
        case "obstacle":
          entityVal = 4 === grid[x][y] ? 0 : 4
          gridCopy[x][y] = entityVal
          updateEntityCountRef("obstacles", entityVal ? 1 : -1)
          break
      }
      return gridCopy
    })
    setGridRef(newGrid)
  }

  const colRes = grid[0].length >= 75 ? 10 : 15
  const rowRes = grid.length >= 75 ? 10 : 15
  const [resolution, setResolution] = useState(Math.min(colRes, rowRes))

  useEffect(() => {
    setResolution(Math.min(colRes, rowRes))
  }, [colRes, rowRes])

  const gridStyle = {
    display: "grid",
    gridTemplateColumns: `repeat(${grid[0].length}, ${resolution}px)`,
    gridTemplateRows: `repeat(${grid.length}, ${resolution}px)`,
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
            resolution={resolution}
          />)
      )}
    </div>
  )
}

export default ConfigGrid