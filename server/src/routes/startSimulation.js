const express = require('express');
const uuid = require("uuid")

const { OK } = require('../const/httpStatus')
const { PENDING } = require('../const/resultStatus')

const {createSimulationRequest} = require('../utils/createSimulationRequest')
const router = express.Router()

router.post('/', async (req, res) => {
  const id = uuid.v4()
  const result = {
    id,
    status : PENDING,
    simulation : {},
  }
  
  res.status(OK).send(result)
  return createSimulationRequest(req.body, result)
})

exports.startSimulationRouter = router

