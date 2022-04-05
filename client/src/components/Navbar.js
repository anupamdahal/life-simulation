import { Link } from "react-router-dom"

const Navbar = () => {
  return (
    <nav className="navbar">
      <h1 className="navbar-text">Life Simulation</h1>
      <div className="navbar-links">
        <Link to="/" className="navbar-text link">Home</Link>
        <span className="navbar-separator"></span>
        <Link to="/about" className="navbar-text link">About</Link>
        <span className="navbar-separator"></span>
        <Link to="/Scoreboard" className="navbar-text link">Scoreboard</Link>
      </div>
    </nav>
  )
}

export default Navbar