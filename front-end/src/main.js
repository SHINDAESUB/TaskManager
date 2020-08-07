import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import axios from 'axios'
import Vuelidate from 'vuelidate'
import { library as faLibrary } from '@fortawesome/fontawesome-svg-core'
import { faHome, faSearch, faPlus, faEllipsisH, faUserPlus, faListUl } from '@fortawesome/free-solid-svg-icons'
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome'

// axios 설정
axios.defaults.baseURL = '/api'  //모든 요청에 '/api' 붙도록 기본 설정
axios.defaults.headers.common.Accept = 'application/json' // JSON 형식으로만 받는다
axios.interceptors.response.use(  //Error 전파하기 위해 인터셉터 응답을 추가한다.
  response => response,
  (error) => {
    return Promise.reject(error)
  }
)

//Vuelidate 세팅
Vue.use(Vuelidate)

// FontAwesome 세팅
faLibrary.add(faHome, faSearch, faPlus, faEllipsisH, faUserPlus, faListUl)
Vue.component('font-awesome-icon', FontAwesomeIcon)


Vue.config.productionTip = false

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
