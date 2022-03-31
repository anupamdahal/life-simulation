#!/bin/sh
PROTOS_DIR=../protos
GRPC_DIR=./src
STYLE=commonjs_strict

mkdir -p src/protos

# Generate JavaScript code
grpc_tools_node_protoc \
    --js_out=import_style=${STYLE},binary:${GRPC_DIR} \
    --grpc_out=${GRPC_DIR} \
    --plugin=protoc-gen-grpc=./node_modules/.bin/grpc_tools_node_protoc_plugin \
    -I ../ \
    ${PROTOS_DIR}/*.proto