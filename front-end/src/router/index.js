import Vue from 'vue'
import Router from 'vue-router'
import HomePage from '@/views/HomePage'
import LoginPage from '@/views/LoginPage'
import JoinPage from '@/views/JoinPage'
import BoardPage from '@/views/BoardPage'

Vue.use(Router)

export default new Router({
  mode: 'history',
  base: process.env.BASE_URL,
  routes: [{
    path: '/',
    name: 'home',
    component: HomePage
  }, {
    path: '/login',
    name: 'login',
    component: LoginPage
  }, {
    path: '/join',
    name: 'join',
    component: JoinPage
  }, {
    path: '/board/:boardId',
    name: 'board',
    component: BoardPage
  }]
})
