import express from 'express'
import { PORT } from './config'

const app = express()

app.get('/', (req, res) => {
  res.send('We are Home')
})

// app.post()

app.listen(PORT)