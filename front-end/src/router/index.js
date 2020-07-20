import Vue from 'vue'
import Router from 'vue-router'
import LoginPage from '@/views/LoginPage'
import JoinPage from '@/views/JoinPage'

Vue.use(Router)

export default new Router({
  mode: 'history',
  base: process.env.BASE_URL,
  routes: [{
    path: '/login',
    name: 'LoginPage',
    component: LoginPage
  }, {
    path: '/join',
    name: 'JoinPage',
    component: JoinPage
  }]
})
