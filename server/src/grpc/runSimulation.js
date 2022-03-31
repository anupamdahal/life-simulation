const { client } = require('./client')
const proto = require('../protos/solution_pb')
const { SIMULATOR_HOST } = require('../../config')

exports.runSimulation = (condition) => 
  new Promise((resolve, reject) => {
    const initialConditions = new proto.InitialConditions()
    initialConditions.setCondition(condition)
    client(SIMULATOR_HOST).runSimulation(initialConditions, (err, result) =>
      err ? console.error(err) : resolve(result)    
    )
})
