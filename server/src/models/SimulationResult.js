const mongoose = require('mongoose')

const SimulationResultSchema = mongoose.Schema({
  status: {
    type: String,
    required: true
  },
  result : Object
})

exports.SimulationResult = mongoose.model('SimulationResult', SimulationResultSchema)