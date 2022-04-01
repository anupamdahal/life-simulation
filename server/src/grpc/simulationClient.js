const { SimulationClient } = require('../protos/simulation_grpc_pb')
const { credentials } = require('grpc')

exports.simulationClient = url => new SimulationClient(
  url,
  credentials.createInsecure(),
)