import axios from 'axios'

export default {
  join (detail) {
    return new Promise((resolve, reject) => {
      axios.post('/join', detail).then(({data}) => {
        resolve(data)
      }).catch((error) => {
        reject(error)
      })
    })
  }
}
