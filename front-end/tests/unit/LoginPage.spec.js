import Vue from 'vue'
import LoginPage from '@/views/LoginPage.vue'

describe('LoginPage.vue', () => {     //jest 의 describe (name , fn ) 하나의 테스를 만든다.
  it('should render correnct contents', () => {  //jest의 it (name , fn , timeout )  == 테스트명, 테스트의 예상값을 포함 함수 , 타임아웃 기본값 5초
    const Constructor = Vue.extend(LoginPage)  // LoginPage 생성 ,
      const vm = new Constructor().$mount()    // Vue 인스턴스를 생성 후 부착 , mount 가 실행되면
      expect(vm.$el.querySelector('h1').textContent).toEqual('TaskManager') // h1 태그의 있는 텍스트 값이 'TaskManager' 인지 확인한다.
    })
})
