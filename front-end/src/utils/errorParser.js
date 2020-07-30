import _ from 'lodash'

export default {
  parse (error) {
    if (error.response) {
      const status = error.response.status
      const data = error.response.data
      if (status === 400) {
        if (data && data.message) {
          return new Error(data.message)
        } else {
          return new Error('잘못된 요청입니다.')
        }
      } else if (status === 401) {
        return new Error('승인되지 않은 요청 입니다.')
      } else if (status === 403) {
        return new Error('금지된 요청 입니다.')
      } else if (status === 404) {
        return new Error('요청 실패 : 서버에서 요청 엔드 포인트를 찾을 수 없습니다. ')
      } else if (status === 500) {
        if (data && data.message) {
          return new Error(data.message + ' 나중에 다시 시도하십시오.')
        } else {
          return new Error('서버에 오류가 있습니다. 나중에 다시 시도하십시오.')
        }
      } else {
        return new Error('요청 실패. 나중에 다시 시도하십시오.')
      }
    } else if (error.request) {
      // Request was made and no response
      return new Error('요청이 실패했습니다. 서버에서 응답이 없습니다.')
    } else {
      return _.isError(error) ? error : new Error(error)
    }
  }
}