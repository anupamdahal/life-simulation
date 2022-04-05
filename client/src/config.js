const SERVER_HOST = process.env.NODE_ENV === 'prod' ? 'server' : 'localhost'
const SERVER_PORT = process.env.SERVER_PORT || 8080
export const SERVER_URL = `${SERVER_HOST}:${SERVER_PORT}`