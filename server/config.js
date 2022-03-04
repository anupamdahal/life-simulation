import dotenv from 'dotenv'

export const ENV_PATH = '../.env'

dotenv.config({
  path: ENV_PATH
})

export const PORT = process.env.REST_API_PORT || 8080