export default {
  join (detail) {
    return new Promise((resolve, reject) => {
      detail.emailAddress === 'weotjqw@naver.com'
        ? resolve({result: 'success'})
        : reject(new Error('이메일이 존재합니다.'))
    })
  }
}
