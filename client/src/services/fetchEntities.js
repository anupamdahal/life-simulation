import services, { getScores } from "../services/services"

export const fetchEntities = (request) => {
  return services
    .postConfigs(request)
    .then(res => {
      if (!res?._id) {
        return Promise.reject("Request Id Not Received")
      }
      return res._id
    })
    .then(id => getScores(id))
    .then(simResult => {
      if (simResult.error !== "None") {
        return Promise.reject(simResult.error)
      }
      return simResult.entities
    })
}