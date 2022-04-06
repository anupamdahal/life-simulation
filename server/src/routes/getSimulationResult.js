const {SimulationResult} = require('../models/SimulationResult')
const express = require('express');
const { NOT_FOUND, OK } = require('../const/httpStatus')
const { INVALID_ID } = require('../const/error');
const { safeResolve } = require('../utils/safeResolve');

const router = express.Router()

router.get('/:id', async (req, res) => {
  const { id } = req.params
  const [simulationResult, err] = await safeResolve(SimulationResult.findById(id))

  if(err){
    res.status(NOT_FOUND).send(INVALID_ID)
    return
  }
  res.status(OK).json(simulationResult)
})

exports.getSimulationResultRouter = router

