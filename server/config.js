const dotenv = require('dotenv')

const path = process.env.NODE_ENV ? `../.env.${
  process.env.NODE_ENV
}` : `../.env`

exports.ENV = dotenv.config({path})

const SIMULATOR_PORT = process.env.SIMULATOR_PORT || 50051

const DB_HOST = process.env.MONGODB_HOST || process.env.LOCAL_HOST || 'localhost'
const DB_PORT = process.env.MONGODB_PORT || 27017
const DB = process.env.MONGO_INITDB_DATABASE || 'simulation'
exports.DB_URL = `mongodb://${DB_HOST}:${DB_PORT}/${DB}`
console.log(this.DB_URL)

exports.PORT = process.env.SERVER_PORT || 8080
exports.SIMULATOR_HOST = `localhost:${SIMULATOR_PORT}`

exports.DB_CONFIG = {
  useNewUrlParser: true
}
