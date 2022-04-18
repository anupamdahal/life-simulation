const express = require('express')

const { SimulationResult } = require('../models/SimulationResult')
const { OK, INTERNAL_SERVER_ERROR } = require('../const/httpStatus')
const { PENDING } = require('../const/resultStatus')
const { DB_ERROR } = require('../const/error')

const {safeResolve} = require('../utils/safeResolve')
const {createSimulationRequest} = require('../utils/createSimulationRequest')

const router = express.Router()

router.post('/', async (req, res) => {

  const simulationResult = new SimulationResult({
    status : PENDING,
    simulation : {},
  })
  const [entry, err] = await safeResolve(simulationResult.save())

  if(err){
    res
      .status(INTERNAL_SERVER_ERROR)
      .send(DB_ERROR)
    return
  }
  res.status(OK).send(entry)

  return createSimulationRequest(req.body, entry)
})

exports.startSimulationRouter = router

