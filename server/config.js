const dotenv = require('dotenv')

exports.ENV    = dotenv.config({path: '../.env'})

const SIMULATOR_PORT = process.env.SIMULATOR_PORT || 50051

const DB_USER  = process.env.MONGO_INITDB_ROOT_USERNAME
const DB_PASS  = process.env.MONGO_INITDB_ROOT_PASSWORD
const DB_PORT  = process.env.MONGODB_PORT || 27017
const DB       = process.env.MONGO_INITDB_DATABASE || 'simulation'
exports.DB_URL   = `mongodb://${DB_USER}:${DB_PASS}@localhost:${DB_PORT}/${DB}`

exports.PORT   = process.env.SERVER_PORT || 8080
exports.SIMULATOR_HOST = `${process.env.LOCAL_HOST}:${SIMULATOR_PORT}`