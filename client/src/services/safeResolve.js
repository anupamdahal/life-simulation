export const safeResolve = async promise => {
  try {
    const res = await promise
    return [res, null]
  } catch (error) {
    return [null, error]
  }
}