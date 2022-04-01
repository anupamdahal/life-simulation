const { Struct } = require('google-protobuf/google/protobuf/struct_pb')
const { simulationClient } = require('./simulationClient')
const { InitialConditions } = require('../protos/simulation_pb')
const { SIMULATOR_HOST } = require('../../config')

exports.runSimulation = (condition) => 
  new Promise((resolve, reject) => {
    const struct = Struct.fromJavaScript(condition)
    const initialConditions = new InitialConditions()
    initialConditions.setConditions(struct)
    
    const client = simulationClient(SIMULATOR_HOST)
    client.runSimulation( initialConditions, (err, res) =>
      err ? console.error(err) : resolve(res.result)    
    )
})
