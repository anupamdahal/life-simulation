import React from "react"

const Cell = ({ isActive, x, y, cellClickRef, entityKind }) => {

  let color

  switch (entityKind) {
    case 0:
      color = "white"
      break
    case 1:
      color = "red"
      break
    case 2:
      color = "yellow"
      break
    case 3:
      color = "green"
      break
    case 4:
      color = "black"
      break
  }

  const cellStyle = {
    backgroundColor: `${isActive ? `${color}` : "white"}`,
    border: ".5px solid black",
    width: "15px",
    height: "15px"
  }

  return <div onClick={() => cellClickRef(x, y)} style={cellStyle}></div>
}

export default Cell