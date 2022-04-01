const {runSimulation} = require('../grpc/runSimulation')
const {db} = require('../db')
const {READY, FAILED} = require('../const/resultStatus')
const {safeResolve} = require('./safeResolve')
const {SimulationResultSchema} = require('../models/SimulationResult')

const createSimulationRequest = async (reqBody, result) => {
  const {initialConditions} = reqBody
  const {id} = result
  db[id] = await result
  console.log(db)
  const [simulationResult, err] = await safeResolve(runSimulation(initialConditions))
  if (err) {
    result.status = FAILED
  } else {
    result.status = READY
    result.simulation = simulationResult || {}
  }
  db[id] = result
}

exports.createSimulationRequest = createSimulationRequest
