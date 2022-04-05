import { Link } from "react-router-dom"

const Home = () => {
  return (
    <div className="home">
      <h1>Life Simulation</h1>
      <Link to="/config" className="link">Start</Link>
    </div>
  )
}

export default Home