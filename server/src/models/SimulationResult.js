const mongoose = require('mongoose')

const SimulationResultSchema = mongoose.Schema({
  status: {
    type: String,
    required: true
  },
  error: {
    type: Object,
    required: false
  },
  result : {
    type: Object,
    required: false
  }
})

exports.SimulationResult = mongoose.model('SimulationResult', SimulationResultSchema)