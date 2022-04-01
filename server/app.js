const express = require('express')
const { PORT } = require('./config')
const bodyParser = require('body-parser')

const { getSimulationResultRouter } = require('./src/routes/getSimulationResult')
const { startSimulationRouter } = require('./src/routes/startSimulation')
const app = express()

app.use(express.json())

app.use('/getSimulationResult', getSimulationResultRouter)
app.use('/startSimulation', startSimulationRouter)

app.get('/', (req, res) => {
  res.send('We are Home')
})

// app.post()

app.listen(PORT)

// const {runSimulation} = require('./src/grpc/runSimulation')

// const initialConditions = {
//   "x": 2,
//   "y": 3,
// }

// runSimulation(initialConditions)
//   .then(res => console.log(res))