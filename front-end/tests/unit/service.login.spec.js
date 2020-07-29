import moxios from 'moxios'
import loginService from '@/service/login'

describe('service/login', () => {
  beforeEach(() => {
    moxios.install()
  })

  afterEach(() => {
    moxios.uninstall()
  })

  it('API `/login` 호출 ', () => {
    expect.assertions(1)
    moxios.wait(() => {
      let request = moxios.requests.mostRecent()
      expect(request.url).toEqual('/login')
      request.respondWith({
        status: 200,
        response: {result: 'success'}
      })
    })
    return loginService.login()
  })

  it('API "/login"  호출 성공 메시지 확인 테스트', () => {
    expect.assertions(2)
    moxios.wait(() => {
      let request = moxios.requests.mostRecent()
      expect(request).toBeTruthy()
      request.respondWith({
        status: 200,
        response: {result: 'success'}
      })
    })
    return loginService.login().then(data => {
      expect(data.result).toEqual('success')
    })
  })

  it('API "/login"  호출 실패 메시지 확인 테스트', () => {
    expect.assertions(2)
    moxios.wait(() => {
      let request = moxios.requests.mostRecent()
      expect(request).toBeTruthy()
      request.reject({
        status: 400,
        response: {message: '인증 실패'}
      })
    })
    return loginService.login().catch(error => {
      expect(error.response.message).toEqual('인증 실패')
    })
  })
})