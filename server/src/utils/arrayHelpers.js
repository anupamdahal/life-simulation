const reshapeArray = (arr, r, c) => {

  if (r * c !== arr.length) {
     return arr
  }
   const newArr = []
   while(arr.length) newArr.push(arr.splice(0,c));
   return newArr
}

exports.reshapeArray = reshapeArray