
import axios from 'axios'
import errorParser from '@/utils/errorParser'

export default {
  /**
   * 카드 추가
   */
  add (detail) {
    return new Promise((resolve, reject) => {
      axios.post('/cards', detail).then(({data}) => {
        resolve(data)
      }).catch((error) => {
        reject(errorParser.parse(error))
      })
    })
  },
 /**
  * 카드 위치 변경
  */
  changePositions (positionChanges) {
    return new Promise((resolve, reject) => {
      axios.post('/cards/positions', positionChanges).then(({data}) => {
        resolve(data)
      }).catch((error) => {
        reject(errorParser.parse(error))
      })
    })
  }
}