import axios from 'axios'
import { safeResolve } from './safeResolve'
import { SERVER_URL } from '../config'
import { START_SIMULATION, GET_SIMULATION_RESULT } from '../const/enumAPIEndPoints'
import { FAILED, PENDING } from '../const/enumAPIStatus'

const postData = (URI, body) => {
  const data = JSON.stringify(body);

  const config = {
    method: 'post',
    url: URI,
    headers: {
      'Content-Type': 'application/json'
    },
    data: data
  };

  return axios(config)
    .then(res => res.data)
}

export const getSimulationResult = (id) => (
  axios.get(`${SERVER_URL}/${GET_SIMULATION_RESULT}/${id}`)
    .then(res => res.data)
)

const getScores = (id) => {
  return new Promise(async (resolve, reject) => {
    const getResult = async () => {
      const [res, err] = await safeResolve(getSimulationResult(id))
      if (err || res.status === FAILED){
        reject(err)
        return
      }
      if (res.status !== PENDING){
        resolve(res.result)
      }
      else{
        setTimeout(getResult, 200)
      }
    }
    getResult()
  })
}

const postConfigs = configs => {
  const initialConditions = {
    initialConditions: configs
  }
  return postData(`${SERVER_URL}/${START_SIMULATION}`, initialConditions)
}

const services = { getScores, postConfigs }
export default services
export { getScores }