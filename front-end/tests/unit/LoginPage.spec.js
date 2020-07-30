import { mount, createLocalVue } from '@vue/test-utils'
import Vuelidate from 'vuelidate'
import VueRouter from 'vue-router'
import LoginPage from '@/views/LoginPage'
import loginService from '@/service/login'

// 로컬 Vue 입력값 확인(Vuelidate 를 이용)
const localVue = createLocalVue()
localVue.use(Vuelidate)
localVue.use(VueRouter)
const router = new VueRouter()

// Mock 로그인 서비스 생성
jest.mock('@/service/login')

describe('LoginPage.vue', () => {
  let wrapper
  let fieldUsername
  let fieldPassword
  let buttonSubmit
  let loginSpy

  beforeEach(() => {
    wrapper = mount(LoginPage, {
      localVue,
      router
    })
    fieldUsername = wrapper.find('#username')
    fieldPassword = wrapper.find('#password')
    buttonSubmit = wrapper.find('form button[type="submit"]')
    // login 서비스 Spy 생성 한다. (서비스이름 ,메소드 이름)
    loginSpy = jest.spyOn(loginService, 'login')
  })

  afterEach(() => {
    loginSpy.mockReset()
    loginSpy.mockRestore()
  })

  afterAll(() => {
    jest.restoreAllMocks()
  })

  it('Login form 렌더링 확인 테스트', () => {
    expect(fieldUsername.element.value).toEqual('')
    expect(fieldPassword.element.value).toEqual('')
    expect(buttonSubmit.text()).toEqual('로그인')
    expect(wrapper.find('.link-sign-up').attributes().href)
      .toEqual('/join')
    expect(wrapper.find('.link-forgot-password'))
      .toBeTruthy()
  })

  it('data 초기값 테스트', () => {
    expect(wrapper.vm.form.username).toEqual('')
    expect(wrapper.vm.form.password).toEqual('')
  })

  it('"submitForm" 버튼 확인', () => {
    const stub = jest.fn()
    wrapper.setMethods({submitForm: stub})
    buttonSubmit.trigger('submit')
    expect(stub).toBeCalled()
  })

  it('데이터 검증 성공 테스트', async () => {
    expect.assertions(2)
    const stub = jest.fn()
    wrapper.vm.$router.push = stub
    wrapper.vm.form.username = 'testname'
    wrapper.vm.form.password = 'testpassword!'
    wrapper.vm.submitForm()
    expect(loginSpy).toBeCalled()
    await wrapper.vm.$nextTick()
    expect(stub).toHaveBeenCalledWith({name: 'home'})
  })

  it('잘못된 데이터 제출을 방지하기 위해 유효성 검사 테스트', () => {

    wrapper.vm.submitForm()
    expect(loginSpy).not.toHaveBeenCalled()

    wrapper.vm.form.username = 'testname'
    wrapper.vm.submitForm()
    expect(loginSpy).not.toHaveBeenCalled()

    wrapper.vm.form.username = 'test@test.com'
    wrapper.vm.submitForm()
    expect(loginSpy).not.toHaveBeenCalled()

    wrapper.vm.form.password = 'testpassword!'
    wrapper.vm.form.username = ''
    wrapper.vm.submitForm()
    expect(loginSpy).not.toHaveBeenCalled()
  })	
})
