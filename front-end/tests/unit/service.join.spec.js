import moxios from 'moxios'
import joinService from '@/service/join'

describe('serive/join', () => {
  beforeEach(() => {
    moxios.install()   //테스트를 위한 목 생성
  })

  afterEach(() => {
    moxios.uninstall()  //테스트 이후 목 제거
  })
  
//요청한 데이터 정상적으로 응답 테스트
  it('9', () => {
    expect.assertions(2)  //jest API 활용
    moxios.wait(() => {   //목 요청이 만들어질 떄까지 기다린다.
      let request = moxios.requests.mostRecent()
      expect(request).toBeTruthy()
      request.respondWith({
        status: 200,
        response: {result: 'success'}
      })
    })
    return joinService.join().then(data => {
      expect(data.result).toEqual('success')
    })
  })



  // it('요청한 데이터가 실패하는 응답 테스트', () => {
  //   expect.assertions(2)
  //   moxios.wait(() => {
  //     let request = moxios.requests.mostRecent()
  //     expect(request).toBeTruthy()
  //     request.reject({
  //       status: 400,
  //       response: {message: 'Bad request'}
  //     })
  //   })
  //   return joinService.join().catch(error => {
  //      expect(error.response.message).toEqual('Bad request')
  //   })
  // })

// `/join` API 호출
  it('5', () => {
    expect.assertions(1)
    moxios.wait(() => {
      let request = moxios.requests.mostRecent()
      expect(request.url).toEqual('/join')
      request.respondWith({
        status: 200,
        response: {result: 'success'}
      })
    })
    return joinService.join()
  })

})
