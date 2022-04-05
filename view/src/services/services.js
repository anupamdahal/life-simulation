import axios from 'axios'

const baseURL = "https://locahost:3001/"

const getScores = () => {
  const req = axios.get("/api/scores")
  return req.then(res => res.data)
}

const postConfigs = configs => {
  const req = axios.post(`${baseURL}/api/simulation`, configs)
  return req.then(res => res.data)
}

const services = { getScores, postConfigs }
export default services