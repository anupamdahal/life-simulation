const { db } = require('../db')
const express = require('express');
const { NOT_FOUND, OK } = require('../const/httpStatus')

const router = express.Router()

router.get('/:id', async (req, res) => {
  const { id } = req.params
  console.log(id)
  const simulationResult = await(db[id])
  console.log(db)
  if(!simulationResult){
    res.status(NOT_FOUND).send({
      error: 'invalid id'
    })
    return
  }

  res.status(OK).send({
    result: simulationResult
  })
})

exports.getSimulationResultRouter = router

