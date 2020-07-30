import axios from 'axios'
import errorParser from '@/utils/errorParser'

export default {
  join (detail) {
    return new Promise((resolve, reject) => {
      axios.post('/join', detail).then(({data}) => {
        resolve(data)
      }).catch((error) => {
        reject(errorParser.parse(error))
      })
    })
  }
}
