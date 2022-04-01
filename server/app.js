const express = require('express')
const mongoose = require('mongoose')
const { PORT, DB_URL } = require('./config')

const { getSimulationResultRouter } = require('./src/routes/getSimulationResult')
const { startSimulationRouter } = require('./src/routes/startSimulation')
const app = express()

mongoose.connect(
  DB_URL,
  {
    useNewUrlParser: true
  },
  () => {
    console.log(`Connected to DB`)
  })

app.use(express.json())
app.use('/getSimulationResult', getSimulationResultRouter)
app.use('/startSimulation', startSimulationRouter)

app.listen(PORT, () =>{
  console.log(`Alive in port ${PORT}`)
})