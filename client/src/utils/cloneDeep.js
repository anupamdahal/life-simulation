export const arrayCloneDeep = (items) => items.map(item => Array.isArray(item) ? clone(item) : item)