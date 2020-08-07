import { mount,createLocalVue } from '@vue/test-utils'  //npm run test:unit 실행
import JoinPage from '@/views/JoinPage'
import VueRouter from 'vue-router'
import Vuelidate from 'vuelidate'
import joinService from '@/service/join'



//vm.$router 에 접근하기 위함 ,테스트에 Vue Router 추가
const localVue = createLocalVue()
localVue.use(Vuelidate)
localVue.use(VueRouter)
const router = new VueRouter()

// 등록 서비스의 목
jest.mock('@/service/join')

describe( 'JoinPage.vue' , ()=> {
  let wrapper
  let fieldUserName
  let fieldEmailAddress
  let fieldFirstName
  let fieldLastName
  let fieldPassword
  let buttonSubmit
  let joinSpy

  beforeEach(()=> {
    wrapper = mount(JoinPage,{ //mount 함수는 마운트와 렌더링이 완료된 컴포넌트를 포함하는 Wrapper 생성
      localVue,
      router
    })
    fieldUserName = wrapper.find('#username')  //wrapper.find 는 선택자에 해당하는 HTML 요소를 찾아주는 API
    fieldEmailAddress = wrapper.find('#emailAddress')
    fieldFirstName = wrapper.find('#firstName')
    fieldLastName = wrapper.find('#lastName')
    fieldPassword = wrapper.find('#password')
    buttonSubmit = wrapper.find('form button[type="submit"]')
    // spy 회원 가입 서비스 생성
    joinSpy = jest.spyOn(joinService, 'join')

  })

  afterEach(() => {
    joinSpy.mockReset()
    joinSpy.mockRestore()
  })

  // 모든 테스트가 완료돠면 호출
  afterAll(() => {
    jest.restoreAllMocks()
  })

  it('등록 페이지 렌더링 테스트', () => {
    expect(fieldUserName.element.value).toEqual('')
    expect(fieldEmailAddress.element.value).toEqual('')
    expect(fieldFirstName.element.value).toEqual('')
    expect(fieldLastName.element.value).toEqual('')
    expect(fieldUserName.element.value).toEqual('')
    expect(buttonSubmit.text()).toEqual('계정생성')
   })

   it('data model 초기값 테스트', () => {
     expect(wrapper.vm.form.username).toEqual('')  //wrapper.vm 으로 Vue 인스턴스 접근, vm 의 모든 메소드와 프로퍼티에 접근 가능
     expect(wrapper.vm.form.emailAddress).toEqual('')
     expect(wrapper.vm.form.firstName).toEqual('')
     expect(wrapper.vm.form.lastName).toEqual('')
     expect(wrapper.vm.form.password).toEqual('')
   })

   it('submit 핸들러 존재 여부 확인' , () =>{
    const stub =jest.fn()
    wrapper.setMethods({submitForm: stub}) //submitForm 을 stub으로 대체 한다.
    buttonSubmit.trigger('submit') //buttonSubmit 으로 submit 이벤트 호출
    expect(stub).toBeCalled() //stub이 호출 됬는지 검증 한다.
   })

  //  it('새로운 등록 테스트', async () => {
  //   expect.assertions(2)
  //   const stub = jest.fn()
  //   wrapper.vm.$router.push = stub
  //   wrapper.vm.form.username = 'test'
  //   wrapper.vm.form.emailAddress = 'test@naver.com'
  //   wrapper.vm.form.password = 'test'
  //   wrapper.vm.submitForm()
  //   expect(joinSpy).toBeCalled()
  //   await wrapper.vm.$nextTick(() => {
  //     expect(stub).toHaveBeenCalledWith({name: 'login'})
  //   })
  //  })

  //  it('등록 실패 테스트', async () => {
  //   expect.assertions(3)
  //   wrapper.vm.form.username = 'test'
  //   wrapper.vm.form.emailAddress = 'test@naver.com'
  //   wrapper.vm.form.password = 'testpass!'
  //   expect(wrapper.find('.failed').isVisible()).toBe(false)
  //   wrapper.vm.submitForm()
  //   expect(joinSpy).toBeCalled()
  //   await wrapper.vm.$nextTick()
  //   expect(wrapper.find('.failed').isVisible()).toBe(true)
  //  })

   it('email 주소 입력 검증 테스트' , () =>{
    wrapper.vm.form.username = 'test'
    wrapper.vm.form.emailAddress = 'bad-email-address'
    wrapper.vm.form.password = 'testpass!'
    wrapper.vm.submitForm()
    expect(joinSpy).not.toHaveBeenCalled()

   })

   it('유저 실패 테스트', () => {
    wrapper.vm.form.username = 't'
    wrapper.vm.form.emailAddress = 'test@naver.com'
    wrapper.vm.form.password = 'testpass!'
    wrapper.vm.submitForm()
    expect(joinSpy).not.toHaveBeenCalled()
  })

  it('비밀번호 실패 테스트', () => {
    wrapper.vm.form.username = 'test'
    wrapper.vm.form.emailAddress = 'test@naver.com'
    wrapper.vm.form.password = 'bad!'
    wrapper.vm.submitForm()
    expect(joinSpy).not.toHaveBeenCalled()
  })


  //  it('form 입력값과 데이터 바인딩 검증 테스트', () => {
  //   const username ='신대섭'
  //   const emailAddress = 'weotjqw@naver.com'
  //   const password = '123456'

  //   wrapper.vm.form.username = username
  //   wrapper.vm.form.emailAddress = emailAddress
  //   wrapper.vm.form.password =password
  //   expect(fieldUserName.element.value).toEqual(username)
  //   expect(fieldEmailAddress.element.value).toEqual(emailAddress)
  //   expect(fieldPassword.element.value).toEqual(password)
  //  })

})

