const {runSimulation} = require('../grpc/runSimulation')
const {READY, FAILED} = require('../const/resultStatus')
const {safeResolve} = require('./safeResolve')
const {SimulationResult} = require('../models/SimulationResult')
const e = require('express')

const createSimulationRequest = async (reqBody, {_id}) => {
  const {initialConditions} = reqBody
  const [simulationResult, err] = await safeResolve(runSimulation(initialConditions))

  let result = {}
  if (err){
    result = {
      error: err,
      status: FAILED
    }
  }else{
    result = {
      status: READY,
      result: simulationResult
    }
  }
  await SimulationResult.findByIdAndUpdate(_id, result)
}

exports.createSimulationRequest = createSimulationRequest
