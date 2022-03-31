const { ProblemSolverClient } = require('../protos/solution_grpc_pb')
const { credentials } = require('grpc')

exports.client = url => new ProblemSolverClient(
  url,
  credentials.createInsecure(),
)