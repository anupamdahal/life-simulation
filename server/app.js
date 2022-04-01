const { express } = require('express')
const { PORT } = require('./config')

const app = express()

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