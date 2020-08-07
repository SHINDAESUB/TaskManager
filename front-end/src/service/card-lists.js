import axios from 'axios'
import errorParser from '@/utils/errorParser'

export default {
  /**
   * 카드 리스트 추가
   */
  add (detail) {
    return new Promise((resolve, reject) => {
      axios.post('/card-lists', detail).then(({data}) => {
        resolve(data)
      }).catch((error) => {
        reject(errorParser.parse(error))
      })
    })
  },

  /**
   * 
   * 카트 위치 변경
   */
  changePositions (positionChanges) {
    return new Promise((resolve, reject) => {
      axios.post('/card-lists/positions', positionChanges).then(({data}) => {
        resolve(data)
      }).catch((error) => {
        reject(errorParser.parse(error))
      })
    })
  }
}