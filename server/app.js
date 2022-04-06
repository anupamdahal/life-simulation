const express = require('express')
const mongoose = require('mongoose')
const cors = require('cors')
const { PORT, DB_URL, DB_CONFIG } = require('./config')

const { getSimulationResultRouter } = require('./src/routes/getSimulationResult')
const { startSimulationRouter } = require('./src/routes/startSimulation')
const app = express()

mongoose.connect(
  DB_URL,
  DB_CONFIG,
  (err) => {
    err ? console.error(err) 
    : console.log(`Connected to DB`)
  })

app.use(cors())
app.use(express.json())
app.use('/getSimulationResult', getSimulationResultRouter)
app.use('/startSimulation', startSimulationRouter)
app.listen(PORT, () =>{
  console.log(`Alive in port ${PORT}`)
})