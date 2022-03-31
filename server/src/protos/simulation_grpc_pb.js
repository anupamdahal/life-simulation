// GENERATED CODE -- DO NOT EDIT!

'use strict';
var grpc = require('grpc');
var protos_simulation_pb = require('../protos/simulation_pb.js');
var google_protobuf_struct_pb = require('google-protobuf/google/protobuf/struct_pb.js');

function serialize_simulation_InitialConditions(arg) {
  if (!(arg instanceof protos_simulation_pb.InitialConditions)) {
    throw new Error('Expected argument of type simulation.InitialConditions');
  }
  return Buffer.from(arg.serializeBinary());
}

function deserialize_simulation_InitialConditions(buffer_arg) {
  return protos_simulation_pb.InitialConditions.deserializeBinary(new Uint8Array(buffer_arg));
}

function serialize_simulation_Results(arg) {
  if (!(arg instanceof protos_simulation_pb.Results)) {
    throw new Error('Expected argument of type simulation.Results');
  }
  return Buffer.from(arg.serializeBinary());
}

function deserialize_simulation_Results(buffer_arg) {
  return protos_simulation_pb.Results.deserializeBinary(new Uint8Array(buffer_arg));
}


var SimulationService = exports.SimulationService = {
  runSimulation: {
    path: '/simulation.Simulation/RunSimulation',
    requestStream: false,
    responseStream: false,
    requestType: protos_simulation_pb.InitialConditions,
    responseType: protos_simulation_pb.Results,
    requestSerialize: serialize_simulation_InitialConditions,
    requestDeserialize: deserialize_simulation_InitialConditions,
    responseSerialize: serialize_simulation_Results,
    responseDeserialize: deserialize_simulation_Results,
  },
};

exports.SimulationClient = grpc.makeGenericClientConstructor(SimulationService);
