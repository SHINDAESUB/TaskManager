/* 개인 정보 , 작성한 게시판 , 팀 조회 */
import axios from 'axios'
import errorParser from '@/utils/errorParser'

export default {

  getInfoData () {
    return new Promise((resolve, reject) => {
      axios.get('/info').then(({data}) => {
        resolve(data)
      }).catch((error) => {
        reject(errorParser.parse(error))
      })
    })
  },
  signOut () {
    return new Promise((resolve, reject) => {
      axios.post('/logout').then(({data}) => {
        resolve(data)
      }).catch((error) => {
        reject(errorParser.parse(error))
      })
    })
  }  
}