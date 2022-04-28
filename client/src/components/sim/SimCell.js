const SimCell = ({ entityKind, resolution }) => {

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
    backgroundColor: `${color}`,
    border: ".5px solid black",
    width: `${resolution}px`,
    height: `${resolution}px`
  }

  return <div style={cellStyle}></div>
}

export default SimCell