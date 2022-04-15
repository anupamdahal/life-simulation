// GENERATED CODE -- DO NOT EDIT!

'use strict';
var grpc = require('grpc');
var protos_simulation_pb = require('../protos/simulation_pb.js');
var google_protobuf_struct_pb = require('google-protobuf/google/protobuf/struct_pb.js');

function serialize_InitialConditions(arg) {
  if (!(arg instanceof protos_simulation_pb.InitialConditions)) {
    throw new Error('Expected argument of type InitialConditions');
  }
  return Buffer.from(arg.serializeBinary());
}

function deserialize_InitialConditions(buffer_arg) {
  return protos_simulation_pb.InitialConditions.deserializeBinary(new Uint8Array(buffer_arg));
}

function serialize_Report(arg) {
  if (!(arg instanceof protos_simulation_pb.Report)) {
    throw new Error('Expected argument of type Report');
  }
  return Buffer.from(arg.serializeBinary());
}

function deserialize_Report(buffer_arg) {
  return protos_simulation_pb.Report.deserializeBinary(new Uint8Array(buffer_arg));
}


var SimulationService = exports.SimulationService = {
  runSimulation: {
    path: '/Simulation/RunSimulation',
    requestStream: false,
    responseStream: false,
    requestType: protos_simulation_pb.InitialConditions,
    responseType: protos_simulation_pb.Report,
    requestSerialize: serialize_InitialConditions,
    requestDeserialize: deserialize_InitialConditions,
    responseSerialize: serialize_Report,
    responseDeserialize: deserialize_Report,
  },
  // rpc RunSimulation (InitialConditions) returns (Results);
};

exports.SimulationClient = grpc.makeGenericClientConstructor(SimulationService);
