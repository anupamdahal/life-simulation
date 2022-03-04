import dotenv from 'dotenv'
dotenv.config({path: '../.env'})

export const PORT     = process.env.SERVER_PORT || 8080
export const DB_USER  = process.env.MONGO_INITDB_ROOT_USERNAME
export const DB_PASS  = process.env.MONGO_INITDB_ROOT_PASSWORD