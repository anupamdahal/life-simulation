import { SCORES } from "../const/enumLocalStorageKeys"

export const addScore = (name, score) => {
  const rawScores = localStorage.getItem(SCORES)
  const scores = rawScores ? JSON.parse(rawScores) : {}
  const savedScore = scores[name] || 0
  const latestScore = savedScore > score ? savedScore : score
  scores[name] = latestScore 
  localStorage.setItem(SCORES, JSON.stringify(scores))
}

export const getScoreBoard = () => {
  const rawScores = localStorage.getItem(SCORES)
  const scores = rawScores ? JSON.parse(rawScores) : {}
  return Object.keys(scores).map(key => ({
    name: key,
    score: scores[key]
  })) || []
}