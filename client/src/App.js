import { React, useState } from 'react'
import { Route, Routes, Link } from 'react-router-dom'
import Home from './components/Home'
import About from './components/About'
import Simulation from './components/sim/Simulation'
import ConfigWrapper from './components/config/ConfigWrapper'
import Scoreboard from './components/Scoreboard'
import Navbar from './components/Navbar'
import Footer from './components/Footer'

const App = () => {

  return (
    <div className="app-container">
      <div className="content-container">
        <Navbar />
        <Routes>
          <Route path="/" element={<Home />} />
          <Route path="/config" element={<ConfigWrapper />} />
          <Route path="/simulation" element={<Simulation />} />
          <Route path="/about" element={<About />} />
          <Route path="/scoreboard" element={<Scoreboard />} />
        </Routes>
      </div>
      <Footer />
    </div>
  )
}

export default App