// package io.grpc;

/*
 * Copyright 2015 The gRPC Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import grpc.*;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;

import java.io.Console;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import simulator.Simulator;

/**
 * Server that manages startup/shutdown of a {@code Simulator} server.
 */
public class SimulatorServer {
  private static final Logger logger = Logger.getLogger(SimulatorServer.class.getName());

  private Server server;

  private void start() throws IOException {
    /* The port on which the server should run */
    int port = 50051;
    server = ServerBuilder.forPort(port)
        .addService(new SimulationImpl())
        .build()
        .start();
    logger.info("Server started, listening on " + port);
    Runtime.getRuntime().addShutdownHook(new Thread() {
      @Override
      public void run() {
        // Use stderr here since the logger may have been reset by its JVM shutdown hook.
        System.err.println("*** shutting down gRPC server since JVM is shutting down");
        try {
          SimulatorServer.this.stop();
        } catch (InterruptedException e) {
          e.printStackTrace(System.err);
        }
        System.err.println("*** server shut down");
      }
    });
  }

  private void stop() throws InterruptedException {
    if (server != null) {
      server.shutdown().awaitTermination(30, TimeUnit.SECONDS);
    }
  }

  /**
   * Await termination on the main thread since the grpc library uses daemon threads.
   */
  private void blockUntilShutdown() throws InterruptedException {
    if (server != null) {
      server.awaitTermination();
    }
  }

  /**
   * Main launches the server from the command line.
   */
  public static void main(String[] args) throws IOException, InterruptedException {
    final SimulatorServer server = new SimulatorServer();
    server.start();
    server.blockUntilShutdown();
  }

  static class SimulationImpl extends SimulationGrpc.SimulationImplBase {
    private static final Logger logger = Logger.getLogger(SimulationImpl.class.getName());
    private Simulator simulator;

    @Override
    public void runSimulation(InitialConditions req, StreamObserver<Report> responseObserver) {
      logger.info("Received a request");
      simulator = new Simulator();
      simulator.init(req.getConditions());
      simulator.run();
      // simulator.generateDummyReport();
      // Report results = simulator.getSimulationResults();
      Report report = simulator.getSimulationReport();
      responseObserver.onNext(report);
      responseObserver.onCompleted();
      logger.info("Response sent");

    }
  }

}
