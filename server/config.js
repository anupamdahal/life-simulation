const dotenv = require('dotenv')

exports.ENV    = dotenv.config({path: '../.env'})

exports.DB_USER  = process.env.MONGO_INITDB_ROOT_USERNAME
exports.DB_PASS  = process.env.MONGO_INITDB_ROOT_PASSWORD

exports.PORT   = process.env.SERVER_PORT || 8080
exports.SIMULATOR_HOST = `${process.env.LOCAL_HOST}:${process.env.SIMULATOR_PORT}`