const { db } = require('../db')
const express = require('express');
const { NOT_FOUND, OK } = require('../const/httpStatus')
const { INVALID_ID } = require('../const/error')

const router = express.Router()

router.get('/:id', async (req, res) => {
  const { id } = req.params
  const simulationResult = await(db[id])

  if(!simulationResult){
    res.status(NOT_FOUND).send(INVALID_ID)
    return
  }

  res.status(OK).send({
    result: simulationResult
  })
})

exports.getSimulationResultRouter = router

