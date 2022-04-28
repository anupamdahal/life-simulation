const {runSimulation} = require('../grpc/runSimulation')
const {READY, FAILED} = require('../const/resultStatus')
const {safeResolve} = require('./safeResolve')
const {SimulationResult} = require('../models/SimulationResult')
const e = require('express')
const { reshapeArray } = require('./arrayHelpers')

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
    const entities = []
    const r = simulationResult.width
    const c = simulationResult.height
    const l = simulationResult.length
    for(let i = 0; i < l; i++){
      const slicedList = simulationResult.entitiesList.slice((i)*r*c, (i+1)*r*c);
      entities.push(reshapeArray(slicedList, r, c));
    }
    result = {
      status: READY,
      result: {
        entities,
        error: simulationResult.error
      }
    }
  }
  
  const [res, dbError] = await safeResolve(SimulationResult.findByIdAndUpdate(_id, result))
  if(dbError){
    result = {
      error: err,
      status: FAILED
    }

    SimulationResult.findByIdAndUpdate(_id, result)
    .catch(err => console.error(err))
  }
}

exports.createSimulationRequest = createSimulationRequest
