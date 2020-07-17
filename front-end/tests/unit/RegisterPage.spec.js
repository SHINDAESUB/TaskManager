import Vue from 'vue'
import RegisterPage from '@/views/RegisterPage'

describe( 'RegisterPage.vue' , ()=> {
  it('콘텐츠 렌더링 테스트 ', () => {
    const Constructor =Vue.extend(RegisterPage)
    const vm = new Constructor().$mount()
    expect(vm.$el.querySelector('#username').value).toEqual('')
    expect(vm.$el.querySelector('#emailAddress').value).toEqual('')
    expect(vm.$el.querySelector('#password').value).toEqual('')
    expect(vm.$el.querySelector('form button[type="submit"]').textContent).toEqual('계정생성')
  })
})
